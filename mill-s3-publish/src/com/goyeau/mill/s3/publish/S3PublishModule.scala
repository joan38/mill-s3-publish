package com.goyeau.mill.s3.publish

import mill.T
import mill.define.Command
import mill.scalalib.JavaModule
import os.Path
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

trait S3PublishModule extends JavaModule {
  def s3BucketName: T[String]
  def s3BucketKey: T[String]

  def s3Publish(): Command[Unit] =
    T.command(S3PublishModule.publish(assembly().path, s3BucketName(), s3BucketKey()))
}

object S3PublishModule {
  def publish(assembly: Path, bucketName: String, bucketKey: String): Unit = {
    val s3  = S3Client.create()
    val key = bucketKey.replaceAll("^/+", "")
    println(s"Uploading assembly to s3://$bucketName/$key")
    s3.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), assembly.toNIO)
    ()
  }
}
