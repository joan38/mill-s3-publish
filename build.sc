import $ivy.`com.goyeau::mill-git:0.2.0`
import $ivy.`com.goyeau::mill-scalafix:0.2.1`
import $ivy.`io.github.davidgregory084::mill-tpolecat:0.2.0`
import com.goyeau.mill.git.{GitVersionModule, GitVersionedPublishModule}
import com.goyeau.mill.scalafix.StyleModule
import io.github.davidgregory084.TpolecatModule
import mill._
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}

object `mill-s3-publish` extends ScalaModule with TpolecatModule with StyleModule with GitVersionedPublishModule {
  override def scalaVersion = "2.13.4"

  lazy val millVersion        = "0.9.3"
  override def compileIvyDeps = super.compileIvyDeps() ++ Agg(ivy"com.lihaoyi::mill-scalalib:$millVersion")
  override def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"software.amazon.awssdk:s3:2.15.36".exclude("com.fasterxml.jackson.core" -> "jackson-databind"),
    ivy"com.fasterxml.jackson.core:jackson-databind:2.11.2"
  )

  override def publishVersion = GitVersionModule.version(withSnapshotSuffix = true)()
  def pomSettings =
    PomSettings(
      description = "A Mill plugin to publish artifacts to S3",
      organization = "com.goyeau",
      url = "https://github.com/joan38/mill-s3-publish",
      licenses = Seq(License.MIT),
      versionControl = VersionControl.github("joan38", "mill-s3-publish"),
      developers = Seq(Developer("joan38", "Joan Goyeau", "https://github.com/joan38"))
    )
}
