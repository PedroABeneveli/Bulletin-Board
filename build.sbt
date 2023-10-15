scalaVersion := "2.13.8"

lazy val root = (project in file("."))
    .settings(
        name := "BulletinBoard"
    )


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-featurespec" % "3.2.11" % "test"

libraryDependencies ++= Seq(
   "org.backuity.clist" %% "clist-core"   % "3.5.1",
   "org.backuity.clist" %% "clist-macros" % "3.5.1" % "provided")