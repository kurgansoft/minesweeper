ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

val zioVersion = "2.1.16"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.2",
      "com.github.kurgansoft.uiglue" %%% "uiglue" % "372f97cf9f9d37b5a8047efbe5cc3272cfea0527"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)