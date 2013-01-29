import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "actor"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "com.typesafe.akka" % "akka-actor" % "2.0.1")

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings( // Add your own project settings here      
  )

}
