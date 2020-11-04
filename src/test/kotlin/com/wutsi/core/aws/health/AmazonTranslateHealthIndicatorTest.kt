package com.wutsi.core.aws.health

import com.amazonaws.services.translate.AmazonTranslate
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.actuate.health.Status

@RunWith(MockitoJUnitRunner::class)
class AmazonTranslateHealthIndicatorTest{
    @Mock
    private lateinit var translate: AmazonTranslate

    private lateinit var health: AmazonTranslateHealthIndicator


    @Before
    fun setUp() {
        health = AmazonTranslateHealthIndicator(translate)
    }

    @Test
    fun up() {
        Assert.assertEquals(Status.UP, health.health().status)
    }


    @Test
    fun down() {
        val request = health.createRequest()
        Mockito.`when`(translate.translateText(request)).thenThrow(RuntimeException::class.java)

        Assert.assertEquals(Status.DOWN, health.health().status)
    }
}
