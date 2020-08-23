package com.wutsi.core.aws.service

import net.rubyeye.xmemcached.MemcachedClient
import net.rubyeye.xmemcached.XMemcachedClientBuilder
import net.rubyeye.xmemcached.auth.AuthInfo
import net.rubyeye.xmemcached.command.BinaryCommandFactory
import net.rubyeye.xmemcached.utils.AddrUtil








open class MemcacheClientBuilder(
        private val addresses: String,
        private val username: String,
        private val password: String
) {
    fun build(): MemcachedClient {
        val servers = AddrUtil.getAddresses(addresses.replace(",", " "))
        val authInfo = AuthInfo.plain(username, password)

        val builder = XMemcachedClientBuilder(servers)
        servers.forEach{
            builder.addAuthInfo(it, authInfo)
        }
        builder.commandFactory = BinaryCommandFactory()
        builder.connectTimeout = 100

        return builder.build()
    }
}
