name := """akkademy-db"""
organization := "com.akkademy-db"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

// Uncomment to use Akka
libraryDependencies ++= Seq(
  "com.akkademy-db" %% "akkademy-db-common" % "0.0.1-SNAPSHOT",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
