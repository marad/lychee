name := "common"
 
libraryDependencies ++= Seq(
  "io.netty" % "netty-all" % "4.0.28.Final",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "com.google.guava" % "guava" % "18.0",
  "com.google.inject" % "guice" % "4.0",
  "com.nothome" % "javaxdelta" % "2.0.1",
  "com.google.inject" % "guice" % "4.0",
  "com.esotericsoftware" % "kryo-shaded" % "3.0.1",
  "org.scalatest" %% "scalatest" % "2.2.5" % Test,
  "org.mockito" % "mockito-core" % "1.+" % Test
)
