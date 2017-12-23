package object controllers {
  val AppName = "meetupConnector"
  val AppVersion = "0.1"

  val PropFileName = "local.properties"
  val KeyClientId = "MEETUP_CLIENT_ID"
  val KeyClientSecret = "MEETUP_CLIENT_SECRET"
  val KeyApiKey = "MEETUP_API_KEY"
  val KeyAccessToken = "accessToken"
  val KeyRefreshToken = "refreshToken"
  val KeyAuthorization = "Authorization"

  val AuthUrl = "https://secure.meetup.com/oauth2/authorize"
  val TokenUrl = "https://secure.meetup.com/oauth2/access"
  val ServiceUrl = "https://api.meetup.com/self/events?desc=true"
  val RedirectServerPort = 8080
  val RedirectServerAddress = "0.0.0.0"
  val RedirectUrl = s"http://localhost:$RedirectServerPort"
  val DefaultWebServerPort = "8080"
  val WebServerAddress = "0.0.0.0"
}
