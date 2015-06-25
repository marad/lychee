import sbt._
import Keys._

object LycheeBuild extends Build {
  val baseSettings = Defaults.defaultSettings ++ Seq(
    organization := "io.github.marad",
    version := "1.0.0",
    scalaVersion := "2.11.6",
    //resolvers += Resolver.jcenterRepo,
    javacOptions ++= Seq("-source", "1.6", "-target", "1.6")
    )

  lazy val api = project
    .settings(baseSettings:_*)

  lazy val common = project
    .dependsOn(api % "compile->compile;test->test")
    .settings(baseSettings:_*)

  lazy val client = project
    .dependsOn(common % "compile->compile;test->test")
    .settings(baseSettings:_*)

  lazy val server = project
    .dependsOn(common % "compile->compile;test->test")
    .settings(baseSettings:_*)

  lazy val integration = project
    .dependsOn(
      client % "compile->compile;test->test", 
      server % "compile->compile;test->test"
    )
    .settings(baseSettings:_*)

  lazy val testkit = project
    .settings(baseSettings:_*)

  lazy val root = (project in file("."))
    .aggregate(api, common, client, server, integration, testkit)

}
