name := "lychee" 

scalaVersion := "2.11.6"
javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

libraryDependencies ++= Seq(
  "io.netty" % "netty-all" % "4.0.28.Final"
)
