ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

val zioVersion = "2.1.20"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "org.scala-js" %%% "scalajs-dom" % "2.8.1",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.2",
      "com.github.kurgansoft.uiglue" %%% "uiglue" % "061e95d468e916d06e0889715b90f1f2118a7826"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)