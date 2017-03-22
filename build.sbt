name := "Aria2P"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "commons-net"   % "commons-net"    % "3.5",
  "org.scalatest" % "scalatest_2.11" % "2.1.7" % "test",
  "org.yaml"      % "snakeyaml"      % "1.17",
  "com.github.scopt" %% "scopt"      % "3.5.0",
  "org.scalaj" %% "scalaj-collection" % "1.6",
  "com.netaporter" %% "scala-uri"    % "0.4.14"
)

resolvers += Resolver.sonatypeRepo("public")

javacOptions ++= Seq("-encoding", "UTF-8")