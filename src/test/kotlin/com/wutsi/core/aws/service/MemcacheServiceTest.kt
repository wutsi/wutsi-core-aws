package com.wutsi.core.aws.service

import net.rubyeye.xmemcached.MemcachedClient
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MemcacheServiceTest {
    @Mock
    lateinit var client: MemcachedClient

    @InjectMocks
    lateinit var service: MemcacheService

    @Test
    fun get() {
        `when`(client.get<String>("foo")).thenReturn("bar")

        val value = service.get("foo")
        assertEquals("bar", value)
    }

    @Test
    fun put() {
        service.put("foo", "bar", 100)

        verify(client).set("foo", 100, "bar")
    }

    @Test
    fun remove() {
        service.remove("foo")
        verify(client).delete("foo")
    }

}
