lazy val sharedScalacOptions = Seq(
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:_",
  "-unchecked")

lazy val wartremoverOptions = List(
  "Any",
  "AsInstanceOf",
  "DefaultArguments",
  "EitherProjectionPartial",
  "Enumeration",
  "Equals",
  "ExplicitImplicitTypes",
  "FinalCaseClass",
  "FinalVal",
  "ImplicitConversion",
  "IsInstanceOf",
  "JavaConversions",
  "LeakingSealed",
  "MutableDataStructures",
  "NoNeedForMonad",
  "NonUnitStatements",
  "Nothing",
  "Null",
  "Option2Iterable",
  "Overloading",
  "Product",
  "Return",
  "Serializable",
  "StringPlusAny",
  "Throw",
  "ToString",
  "TraversableOps",
  "TryPartial",
  "Var",
  "While").map((s: String) => "-P:wartremover:traverser:org.wartremover.warts." + s)

lazy val nonConsoleOptions =
  wartremoverOptions ++ Seq("-Ywarn-unused-import", -"Xfatal-warnings")

lazy val jvmDependencySettings = Seq.empty

lazy val jsDependencySettings = Seq.empty

lazy val sharedDependencySettings = Seq(
  libraryDependencies ++= Seq(
    compilerPlugin("org.wartremover" %% "wartremover" % "1.2.1"),
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test"))

lazy val sharedSettings =
  sharedDependencySettings ++
  Seq(name := "$name$",
      organization := "$organization$",
      scalaVersion := "$scala_version$",
      scalacOptions := sharedScalacOptions ++ wartremoverOptions,
      scalacOptions in (Compile, console) ~= (_ filterNot (nonConsoleOptions.contains(_))),
      scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value)

lazy val $name$JVMSettings =
  jvmDependencySettings ++
  Seq(scalacOptions ++= Seq("-Ywarn-dead-code"))

lazy val $name$JSSettings =
  jsDependencySettings ++
  Seq(scalacOptions --= Seq("-Ywarn-dead-code"))

lazy val $name$ = crossProject.in(file("."))
  .settings(sharedSettings: _*)

lazy val $name$JVM = $name$.jvm
  .settings($name$JVMSettings: _*)

lazy val $name$JS = $name$.js
  .settings($name$JSSettings: _*)

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .aggregate($name$JVM, $name$JS)
  .settings(
    publish := {},
    publishLocal := {})
