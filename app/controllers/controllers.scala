package object controllers {
  val AppName = "meetupConnector"
  val AppVersion = "0.1"

  val PropFileName = "local.properties"
  val KeyClientId = "meetup.client-id"
  val KeyClientSecret = "meetup.client-secret"
  val KeyApiKey = "meetup.api-key"
  val KeyAccessToken = "accessToken"
  val KeyRefreshToken = "refreshToken"
  val KeyAuthorization = "Authorization"

  val AuthUrl = "https://secure.meetup.com/oauth2/authorize"
  val TokenUrl = "https://secure.meetup.com/oauth2/access"
  val ServiceUrl = "https://api.meetup.com/self/events?desc=true"
  val RedirectServerPort = 9000
  val RedirectServerAddress = "0.0.0.0"
  val RedirectUrl = s"http://localhost:$RedirectServerPort/auth"
  val DefaultWebServerPort = "9000"
  val WebServerAddress = "0.0.0.0"
}
