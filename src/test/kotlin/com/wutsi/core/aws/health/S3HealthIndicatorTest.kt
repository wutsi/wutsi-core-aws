package com.wutsi.core.aws.health

import com.amazonaws.services.s3.AmazonS3
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.actuate.health.Status

@RunWith(MockitoJUnitRunner::class)
class S3HealthIndicatorTest {
    @Mock
    private lateinit var s3: AmazonS3

    private lateinit var health: S3HealthIndicator


    @Before
    fun setUp() {
        health = S3HealthIndicator(s3, "foo")
    }

    @Test
    fun up() {
        `when`(s3.getBucketLocation(anyString())).thenReturn("foo")

        assertEquals(Status.UP, health.health().status)
    }


    @Test
    fun down() {
        `when`(s3.getBucketLocation(anyString())).thenThrow(RuntimeException::class.java)

        assertEquals(Status.DOWN, health.health().status)
    }
}
