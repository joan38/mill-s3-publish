package com.goyeau.mill.s3.publish

import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import mill.T
import mill.define.Command
import mill.scalalib.JavaModule
import os.Path

trait S3PublishModule extends JavaModule {
  def s3BucketName: T[String]
  def s3BucketKey: T[String]

  def s3Publish(): Command[Unit] =
    T.command(S3PublishModule.publish(assembly().path, s3BucketName(), s3BucketKey()))
}

object S3PublishModule {
  def publish(assembly: Path, bucketName: String, bucketKey: String): Unit = {
    val transferManager = TransferManagerBuilder.standard().build()
    val key             = bucketKey.replaceAll("^/+", "")
    val upload          = transferManager.upload(bucketName, key, assembly.toIO)
    println(s"Uploading assembly to s3://$bucketName/$key")
    upload.waitForCompletion()
  }
}
