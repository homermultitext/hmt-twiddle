
// must be at least 2.11 to use hmt_textmodel
scalaVersion := "2.12.1"

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith", "maven")

libraryDependencies ++=   Seq(
  "edu.holycross.shot.cite" %% "xcite" % "1.3.0",
  "edu.holycross.shot" %% "ohco2" % "4.0.0",
  "edu.holycross.shot" %% "orca" % "2.1.0",
  "edu.holycross.shot" %% "greek" % "1.3.1",
  "edu.holycross.shot" %% "gsphone" % "1.0.1"
  //,"org.homermultitext" %% "hmt-textmodel" % "1.1.1"

)