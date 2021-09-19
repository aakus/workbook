name := "workbook"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("io.github.aakus")
Global / excludeLintKeys += idePackagePrefix

scalacOptions ++= Seq(
  "-Ymacro-annotations","-feature","-deprecation"
)

libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "2.2.0")

libraryDependencies += "org.typelevel" %% "cats-effect" % "3.0.2"

libraryDependencies += "co.fs2" %% "fs2-core" % "3.0.1"
libraryDependencies += "co.fs2" %% "fs2-io" % "3.0.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % "0.14.0-M4")

libraryDependencies += "io.circe" %% "circe-generic-extras" % "0.13.1-M4"

libraryDependencies ++= Seq(
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core"   % "2.7.0",
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.7.0" % "provided"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.6",
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.6.0",
  "com.danielasfregola" %% "random-data-generator-magnolia" % "2.9",
  "org.scalamock" %% "scalamock" % "5.1.0",
  "org.mockito" %% "mockito-scala-scalatest" % "1.16.3",
  "org.mockito" %% "mockito-scala-cats" % "1.16.3"
).map(_ % Test)

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.4-2",
  "dev.zio" %% "zio-streams" % "1.0.4-2",
  "dev.zio" %% "zio-prelude" % "1.0.0-RC3"
)

libraryDependencies += "nl.vroste" %% "rezilience" % "0.6.1"

// https://alphabet-soup.typechecked.io/docs/
libraryDependencies += "io.typechecked" %% "alphabet-soup" % "0.3.0"

// https://github.com/TypeChecked/numerology
libraryDependencies += "io.typechecked" %% "numerology" % "0.1.5"