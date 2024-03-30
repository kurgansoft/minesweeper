ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"

val zioVersion = "2.0.21"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.6.0",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.1",
      "com.github.kurgansoft" % "uiglue" % "ee37499c4c3dd9b90b273e747cc29bd86c42e5ad"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)