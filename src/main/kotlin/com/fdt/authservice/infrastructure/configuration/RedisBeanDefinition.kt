package com.fdt.authservice.infrastructure.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fdt.authservice.domain.repository.TokenRepository
import com.fdt.authservice.infrastructure.repository.TokenRedisRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.time.Duration


@Configuration
class RedisBeanDefinition {

    @Value("\${spring.redis.master-url}")
    lateinit var masterUrl: String

    @Value("\${spring.redis.master-port:7000}")
    var masterPort: Int = 0

    @Value("\${spring.redis.ttl:180}")
    var timeToLive: Int = 180

    private fun buildPoolConfig(): JedisPoolConfig {
        val poolConfig = JedisPoolConfig()
        poolConfig.maxTotal = 128
        poolConfig.maxIdle = 128
        poolConfig.minIdle = 16
        poolConfig.testOnBorrow = true
        poolConfig.testOnReturn = true
        poolConfig.testWhileIdle = true
        poolConfig.minEvictableIdleTimeMillis = Duration.ofSeconds(60).toMillis()
        poolConfig.timeBetweenEvictionRunsMillis = Duration.ofSeconds(30).toMillis()
        poolConfig.numTestsPerEvictionRun = 3
        poolConfig.blockWhenExhausted = true
        return poolConfig
    }

    @Bean
    fun jedisPool(): JedisPool {
        return JedisPool(buildPoolConfig(), masterUrl, masterPort)
    }

    @Bean
    fun tokenRepository(jedisPool: JedisPool,
                        objectMapper: ObjectMapper): TokenRepository {
        return TokenRedisRepository(jedisPool, objectMapper, timeToLive)
    }
}