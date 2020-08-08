package com.goyeau.mill.s3.publish

import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import mill.T
import mill.define.Command
import mill.scalalib.JavaModule

trait S3PublishModule extends JavaModule {
  def s3BucketName: T[String]
  def s3BucketKey: T[String]

  def s3Publish(): Command[Unit] =
    T.command {
      val assemblyPath = assembly().path

      val transferManager = TransferManagerBuilder.standard().build()
      val bucketName      = s3BucketName()
      val key             = s3BucketKey().replaceAll("^/+", "")
      val upload          = transferManager.upload(bucketName, key, assemblyPath.toIO)
      T.log.info(s"Uploading assembly to s3://$bucketName/$key")
      upload.waitForCompletion()
    }
}
