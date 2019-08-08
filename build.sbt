inThisBuild(
  Vector(
    scalaVersion := "2.13.0"
  )
)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")


lazy val `fp-scala` = project
  .in(file("."))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-RC1",
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.0-M31",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.19.0"
  )
