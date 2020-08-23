package com.wutsi.core.aws.health

import net.rubyeye.xmemcached.MemcachedClient
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.actuate.health.Status

@RunWith(MockitoJUnitRunner::class)
class MemcacheHealthIndicatorTest {
    @Mock
    lateinit var client: MemcachedClient

    @InjectMocks
    lateinit var health: MemcacheHealthIndicator

    @Test
    fun up() {
        Assert.assertEquals(Status.UP, health.health().status)
    }

    @Test
    fun down() {
        `when`(client.get<String>(MemcacheHealthIndicator.KEY)).thenThrow(RuntimeException("failed"))
        Assert.assertEquals(Status.DOWN, health.health().status)
    }
}
