scalaVersion := "2.12.6"

libraryDependencies += "com.cronutils" % "cron-utils" % "7.0.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0"

/* Logging */
libraryDependencies += "org.log4s" %% "log4s" % "1.6.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime"
libraryDependencies += "org.codehaus.groovy" %  "groovy-all" % "2.4.15" % "runtime"
