ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

val zioVersion = "2.1.21"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.8.1",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.3",
      "com.github.kurgansoft.uiglue" %%% "uiglue" % "846288b0b7840ce2f0741df9191f608e34ca85ca"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)