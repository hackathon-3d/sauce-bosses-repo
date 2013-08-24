import sbt._
import sbt.Keys._
import com.twitter.sbt._
import spray.revolver.RevolverPlugin._

object ProjectBuild extends Build {

  lazy val root = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Revolver.settings ++ Seq(
      name := "PillAlarm",
      organization := "org.saucebosses",
      version := "0.1",
      scalaVersion := "2.10.2",
      mainClass in (Compile, run) := Some("org.saucebosses.pillalarm.App"),

      resolvers += "twitter" at "http://maven.twttr.com/",

      libraryDependencies ++= {
        val finagleVer = "6.5.2"

        Seq(
          "com.twitter" %% "finagle-core" % finagleVer,
          "com.twitter" %% "finagle-stats" % finagleVer,
          "com.twitter" %% "finagle-http" % finagleVer,
          "com.twitter" %% "finagle-mysql" % finagleVer,
          "com.twitter" %% "finagle-memcached" % finagleVer,
          "com.twitter"  % "finatra" % "1.3.7",
          "com.twitter" %% "twitter-server" % "1.0.2",
          "org.mongodb" %% "casbah" % "2.6.2"
        )
      }
    )
  )
}