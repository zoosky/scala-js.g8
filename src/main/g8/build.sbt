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

lazy val jvmDependencySettings = Seq(
  libraryDependencies ++= Seq(
    compilerPlugin("org.wartremover" %% "wartremover" % "1.2.1"),
    "ch.qos.logback"  % "logback-classic" % "1.1.8",
    "org.log4s"      %% "log4s"           % "1.3.4"))

lazy val jsDependencySettings = Seq.empty

lazy val sharedDependencySettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test"))

lazy val sharedSettings = Seq(
  name := "$name$",
  organization := "$organization$",
  scalaVersion := "$scala_version$",
  scalacOptions := sharedScalacOptions) ++ sharedDependencySettings

lazy val $name$JVMSettings = Seq(
  scalacOptions ++= Seq("-Ywarn-dead-code") ++ wartremoverOptions) ++ jvmDependencySettings

lazy val $name$JSSettings = Seq(
  scalacOptions --= Seq("-Ywarn-dead-code")) ++ jsDependencySettings

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
