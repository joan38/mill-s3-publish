# mill-s3-publish

[![Latest version](https://index.scala-lang.org/joan38/mill-s3-publish/mill-s3-publish/latest.svg?color=lightgrey)](https://index.scala-lang.org/joan38/mill-s3-publish/mill-s3-publish)

A Mill plugin to publish artifacts to S3.


## Usage

Publishing assembly to S3.

*build.sc*:
```scala
import $ivy.`com.goyeau::mill-s3-publish:<latest version>`
import com.goyeau.mill.s3.publish.S3PublishModule
import mill.scalalib.JavaModule

object project extends JavaModule with S3PublishModule {
  override def s3BucketName = "my-bucket"
  override def s3BucketKey =
    s"artifacts/com/goyeau/${artifactId()}/$jobVersion()/${artifactId()}-$jobVersion()-assembly.jar"
}
```

```shell script
> mill project.s3Publish
```


## Related projects

* Inspired by [Frugal Mechanic SBT S3 Resolver](https://github.com/frugalmechanic/fm-sbt-s3-resolver)


## Contributing

Contributions are more than welcome!  
See [CONTRIBUTING.md](CONTRIBUTING.md) for all the information and getting help.
