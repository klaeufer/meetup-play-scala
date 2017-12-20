name := """meetup-play-scala"""
organization := "edu.luc.cisr"

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  ws,
  ehcache,
  "com.github.nscala-time" %% "nscala-time" % "2.18.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "com.typesafe.play" %% "play-specs2" % "2.6.9" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "edu.luc.cisr.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.github._"
