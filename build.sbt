ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

lazy val root = (project in file("."))
  .settings(
    Compile / scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "2.8.1",
      "com.github.japgolly.scalajs-react"  %%% "core" % "2.1.3",
      "com.github.kurgansoft.uiglue" %%% "uiglue" % "a01457a9d875e90e93b5eda9beb75cbf4ad991e4"
    ),
    resolvers += "jitpack" at "https://jitpack.io"
  ).enablePlugins(ScalaJSPlugin)