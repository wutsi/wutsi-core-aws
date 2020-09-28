package com.wutsi.core.aws.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ListObjectsRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.wutsi.core.storage.StorageVisitor
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL

@RunWith(MockitoJUnitRunner::class)
class S3StorageServiceTest {
    @Mock
    private lateinit var s3: AmazonS3

    private lateinit var storage: S3StorageService

    @Before
    fun setUp() {
        storage = S3StorageService(s3, "test")
    }

    @Test
    fun contains() {
        assertTrue(storage.contains(URL("https://s3.amazonaws.com/test/document/1/2/text.txt")))
        Assert.assertFalse(storage.contains(URL("https://www.google.com/1/2/text.txt")))
    }


    @Test
    fun store() {
        val content = ByteArrayInputStream("hello".toByteArray())
        val result = storage.store("document/test.txt", content, "text/plain", 31536000)

        assertNotNull(result)
        assertEquals(URL("https://s3.amazonaws.com/test/document/test.txt"), result)

        val request: ArgumentCaptor<PutObjectRequest> = ArgumentCaptor.forClass(PutObjectRequest::class.java)
        Mockito.verify(s3).putObject(request.capture())
        assertEquals(request.value.bucketName, "test")
        assertEquals(request.value.metadata.cacheControl, "max-age=31536000, must-revalidate")
        assertEquals(request.value.metadata.contentType, "text/plain")
    }

    @Test
    fun storeNoContentType() {
        val content = ByteArrayInputStream("hello".toByteArray())
        val result = storage.store("document/test.txt", content, null, 31536000)

        assertNotNull(result)
        assertEquals(URL("https://s3.amazonaws.com/test/document/test.txt"), result)

        val request: ArgumentCaptor<PutObjectRequest> = ArgumentCaptor.forClass(PutObjectRequest::class.java)
        Mockito.verify(s3).putObject(request.capture())
        assertNull(request.value.metadata.contentType)
    }

    @Test
    fun storeNoTTL() {
        val content = ByteArrayInputStream("hello".toByteArray())
        val result = storage.store("document/test.txt", content, "text/plain", null)

        assertNotNull(result)
        assertEquals(URL("https://s3.amazonaws.com/test/document/test.txt"), result)

        val request: ArgumentCaptor<PutObjectRequest> = ArgumentCaptor.forClass(PutObjectRequest::class.java)
        Mockito.verify(s3).putObject(request.capture())
        assertNull(request.value.metadata.cacheControl)
    }

    @Test(expected = IOException::class)
    fun storeWithError() {
        Mockito.`when`(s3.putObject(ArgumentMatchers.any())).thenThrow(RuntimeException::class.java)

        val content = ByteArrayInputStream("hello".toByteArray())
        storage.store("document/test.txt", content, "text/plain")
    }

    @Test
    fun get() {
        val url = "https://s3.amazonaws.com/test/100/document/203920392/toto.txt"
        val os = ByteArrayOutputStream()

        val obj = Mockito.mock(S3Object::class.java)
        val content = Mockito.mock(S3ObjectInputStream::class.java)
        Mockito.`when`(content.read(ArgumentMatchers.any())).thenReturn(-1)
        Mockito.`when`(obj.objectContent).thenReturn(content)
        Mockito.`when`(s3.getObject(ArgumentMatchers.any())).thenReturn(obj)

        storage.get(URL(url), os)

        val request: ArgumentCaptor<GetObjectRequest> = ArgumentCaptor.forClass(GetObjectRequest::class.java)
        Mockito.verify(s3).getObject(request.capture())
        assertEquals(request.value.bucketName, "test")
        assertEquals(request.value.key, "100/document/203920392/toto.txt")

    }

    @Test(expected = IOException::class)
    fun getWithError() {
        Mockito.`when`(s3.getObject(ArgumentMatchers.any())).thenThrow(RuntimeException::class.java)

        val url = "https://s3.amazonaws.com/test/100/document/203920392/toto.txt"
        val os = ByteArrayOutputStream()

        storage.get(URL(url), os)
    }

    @Test
    fun visit () {
        val listings = mock(ObjectListing::class.java)
        `when`(listings.objectSummaries).thenReturn(listOf(
                createObjectSummary("a/file-a1.txt"),
                createObjectSummary("a/file-a2.txt"),
                createObjectSummary("a/b/file-ab1.txt"),
                createObjectSummary("a/b/c/file-abc1.txt")
        ))
        `when`(s3.listObjects(ArgumentMatchers.any(ListObjectsRequest::class.java))).thenReturn(listings)

        val urls = mutableListOf<URL>()
        val visitor = createStorageVisitor(urls)
        val baseUrl = "https://s3.amazonaws.com/test"

        storage.visit("a", visitor)
        assertEquals(4, urls.size)
        assertTrue(urls.contains(URL("$baseUrl/a/file-a1.txt")))
        assertTrue(urls.contains(URL("$baseUrl/a/file-a2.txt")))
        assertTrue(urls.contains(URL("$baseUrl/a/b/file-ab1.txt")))
        assertTrue(urls.contains(URL("$baseUrl/a/b/c/file-abc1.txt")))
    }

    private fun createStorageVisitor(urls: MutableList<URL>) = object: StorageVisitor {
        override fun visit(url: URL) {
            urls.add(url)
        }
    }

    private fun createObjectSummary(key: String): S3ObjectSummary {
        val obj = S3ObjectSummary()
        obj.key = key
        return obj
    }
}
