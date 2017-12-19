# meetup-play-scala

Aims to provide a simple but useful Scala service to communicate with [Meetup API](https://www.meetup.com/meetup_api/clients) and parse the results into Scala case class instances.
See also the [Meetup API v3 documentation](https://www.meetup.com/meetup_api/docs).

*Currently in the exploration stage.*

# How to develop in IntelliJ IDEA CE

Advanced Play support seems to be an IDEA Ultimate Edition feature.
This section describes how to develop Play apps with the Community Edition (CE).

- In the [IntelliJ IDEA section of the Play documentation](https://www.playframework.com/documentation/2.6.x/IDE),
  follow the instructions on creating a run configuration for your Play app.
- Add the necessary environment variables, e.g., `MEETUP_API_KEY`, to your run configuration.
- [This section](https://www.playframework.com/documentation/2.6.x/ProductionConfiguration) explains how to refer to an environment variable in the Play `application.conf`.
