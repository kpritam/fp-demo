scalacOptions += "-Ypartial-unification"

lazy val `fp-demo` = project
  .in(file("."))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0",
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.24",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
  )