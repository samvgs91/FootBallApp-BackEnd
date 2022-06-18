name := """back-end-fottball-app"""
organization := "dcc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
   guice, 
   jdbc,
   "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
   "org.postgresql" % "postgresql" % "42.3.6"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "dcc.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "dcc.binders._"
