package controllers

import javax.inject._

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTime.{parse => parseDateTime}
import play.api._
import play.api.http.{Status => HttpStatus}
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.ExecutionContext
import scala.util.Try
import models._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (config: Configuration, cc: ControllerComponents, ws: WSClient, ec: ExecutionContext) extends AbstractController(cc) {

  implicit val implicitEc = ec

  val logger = Logger(this.getClass)

  val clientId = config.get[String](KeyClientId)
  val clientSecret = config.get[String](KeyClientSecret)
  logger.debug(s"$KeyClientId = $clientId")
  logger.debug(s"$KeyClientSecret = $clientSecret")

  implicit val groupFormat = Json.format[Group]
  implicit val eventFormat = Json.format[Event]
  implicit val effortWrites = new Writes[Effort] {
    def writes(effort: Effort) = Json.obj(
      "from" -> effort.from.getMillis,
      "to" -> effort.to.getMillis,
      "effort" -> effort.duration.getMillis
    )
  }

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request =>
    request.session.get(KeyAccessToken) match {
      case None =>
        val authArgs = Seq(
          "client_id" -> clientId,
          "response_type" -> "code",
          "redirect_uri" -> RedirectUrl
        )
        val authUrlWithArgs = ws.url(AuthUrl).addQueryStringParameters(authArgs: _*).uri
        logger.debug(s"authUrl = $authUrlWithArgs")
        Ok(views.html.index(authUrlWithArgs))
      case Some(_) =>
        Ok(views.html.effortQuery())
    }
  }

  def auth(code: String) = Action.async {
    val tokenArgs = Map(
      "client_id" -> clientId,
      "client_secret" -> clientSecret,
      "grant_type" -> "authorization_code",
      "redirect_uri" -> RedirectUrl,
      "code" -> code
    )
    logger.debug(tokenArgs.toString)
    ws.url(TokenUrl).post(tokenArgs).map { response =>
      val json = Json.parse(response.body)
      logger.debug(Json.prettyPrint(json))
      val accessToken = json("access_token").as[String]
      val refreshToken = json("refresh_token").as[String]
      logger.debug(s"storing access and refresh tokens = $accessToken $refreshToken")
      Ok(views.html.effortQuery()).withSession(KeyAccessToken -> accessToken)
    }
  }

  def effort(from: Option[String], until: Option[String]) = Action.async { implicit request =>
    val accessToken = request.session.get(KeyAccessToken).get
    logger.debug(s"found access token $accessToken in session")
    val authHeader = KeyAuthorization -> s"Bearer $accessToken"
    val client = ws.url(ServiceUrl).addHttpHeaders(authHeader)
    logger.debug(s"submitting request to ${client.url}")
    client.get() map { response =>
      logger.debug(response.body)
      response.status match {
        case HttpStatus.OK =>
          val fromDateTime = from map parseDateTime getOrElse DateTime.lastMonth
          val toDateTime = until map parseDateTime getOrElse DateTime.now
          val interval = fromDateTime to toDateTime

          Try {
            val json = Json.parse(response.body)
            // TODO figure out why we need to map explicitly
            // val events = Json.fromJson[IndexedSeq[Event]](json)
            val events = json.as[JsArray].value flatMap { _.validate[Event].asOpt }
            logger.debug(s"found ${events.length} events total")
            val result = Effort(events, interval)
            render {
              case Accepts.Html() => Ok(views.html.message(result.toString))
              case _              => Ok(Json.toJson(result))
            }
          } getOrElse {
            Ok(views.html.message("JSON parse error"))
          }
        case status => Ok(views.html.message(s"it didn't work: $status ${response.body}"))
      }
    } recover {
      PartialFunction(_ => Ok(views.html.message("API request timeout")))
    }
  }
}
