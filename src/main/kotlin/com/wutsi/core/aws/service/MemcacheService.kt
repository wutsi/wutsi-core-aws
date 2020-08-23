package com.wutsi.core.aws.service

import com.wutsi.core.cache.CacheService
import net.rubyeye.xmemcached.MemcachedClient


open class MemcacheService(
        private val client: MemcachedClient
) : CacheService {

    override fun get(key: String) = client.get<String>(key)

    override fun put(key: String, value: String, ttlSeconds: Int) {
        client.set(key, ttlSeconds, value)
    }

    override fun remove(key: String) {
        client.delete(key)
    }


}
