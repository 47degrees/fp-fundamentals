val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.typelevel" %% "cats-core" % "2.8.0"
  )
