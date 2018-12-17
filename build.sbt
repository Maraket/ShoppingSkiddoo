
organization := "au.com.skiddoo"

name := "skiddoo-shopping"

version := "1.0.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.10" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

javacOptions in Compile ++= Seq("-encoding", "utf8")

// excludes Scala related libraries into the dependency
autoScalaLibrary := false

// Do not append Scala versions to the generated artifacts
crossPaths := false


