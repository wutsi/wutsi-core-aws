package com.wutsi.api.storage.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.net.URLEncoder
import java.util.UUID


open class AwsStorageService(
        private val s3: AmazonS3
) : StorageService {
    lateinit var bucket: String

    @Throws(IOException::class)
    override fun store(path: String, content: InputStream, contentType: String?): String {
        val meta = ObjectMetadata()
        meta.contentType = contentType

        val request = PutObjectRequest(bucket, path, content, meta)
        try {
            s3.putObject(request)
            return String.format("https://s3.amazonaws.com/%s/%s", bucket, path)
        } catch (e: Exception){
            throw IOException(String.format("Unable to store to s3://%s/%s", bucket, path), e)
        }

    }


    override fun get(url: String, os: OutputStream) {
        val path = URL(url).path.substring(bucket.length+2)
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
}
