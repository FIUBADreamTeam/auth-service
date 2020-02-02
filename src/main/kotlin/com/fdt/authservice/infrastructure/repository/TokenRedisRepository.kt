package com.fdt.authservice.infrastructure.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository
import redis.clients.jedis.JedisPool

class TokenRedisRepository(private val jedisPool: JedisPool,
                           private val objectMapper: ObjectMapper,
                           private val timeToLive: Int) : TokenRepository {

    private fun keyWithPrefix(key: String): String = "token:$key"

    override fun save(token: Token): Token {
        jedisPool.resource.use {
            it.setex(keyWithPrefix(token.token), timeToLive, objectMapper.writeValueAsString(token))
        }
        return token
    }
}
