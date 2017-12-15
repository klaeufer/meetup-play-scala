name := """meetup-play-scala"""
organization := "edu.luc.cisr"

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-specs2" % "2.6.9" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "edu.luc.cisr.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "edu.luc.cisr.binders._"
