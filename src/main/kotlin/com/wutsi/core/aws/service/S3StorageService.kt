package com.wutsi.core.aws.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.wutsi.core.storage.StorageService
import com.wutsi.core.storage.StorageVisitor
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL


open class S3StorageService(
        private val s3: AmazonS3,
        private val bucket: String
) : StorageService {

    @Throws(IOException::class)
    override fun store(path: String, content: InputStream, contentType: String?): URL {
        val meta = ObjectMetadata()
        meta.contentType = contentType

        val request = PutObjectRequest(bucket, path, content, meta)
        try {
            s3.putObject(request)
            return toURL(path)
        } catch (e: Exception){
            throw IOException(String.format("Unable to store to s3://%s/%s", bucket, path), e)
        }

    }

    override fun get(url: URL, os: OutputStream) {
        val path = url.path.substring(bucket.length+2)
        val request = GetObjectRequest(bucket, path)
        try {
            val obj = s3.getObject(request)
            obj.use {
                obj.objectContent.copyTo(os)
            }
        } catch (e: Exception){
            throw IOException(String.format("Unable to store to s3://%s/%s", bucket, path), e)
        }
    }

    override fun visit(path: String, visitor: StorageVisitor) {
        val listings = s3.listObjects(path)
        listings.objectSummaries.forEach { visitor.visit(toURL(it.key)) }
    }

    private fun toURL(path: String) = URL(String.format("https://s3.amazonaws.com/%s/%s", bucket, path))
}
