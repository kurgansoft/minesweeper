ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

val zioVersion = "2.1.17"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.2",
      "com.github.kurgansoft.uiglue" %%% "uiglue" % "abcd4d930855077f2658210c3a4c1d9601b9c636"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)