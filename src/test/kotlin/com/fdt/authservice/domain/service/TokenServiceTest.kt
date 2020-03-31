package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.time.Clock
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class TokenServiceTest {

    private lateinit var tokenRepository: TokenRepository
    private lateinit var clock: Clock
    private lateinit var tested: TokenService

    @Before
    fun setUp() {
        tokenRepository = mock(TokenRepository::class.java)
        clock = mock(Clock::class.java)
        tested = TokenService(tokenRepository, clock)
    }

    @Test
    fun `when try to get subject with a valid token should get it`() {
        val subject = 1L
        val token = validToken(subject)

        assertEquals(subject.toString(), tested.getSubject(token.token))
    }

    @Test
    fun `when try to get subject with an expired token throw ExpiredJwtException`() {
        val token = expiredToken()

        assertFailsWith<ExpiredJwtException> {
            tested.getSubject(token.token)
        }
    }

    @Test
    fun `when try to get subject with an invalid token throw MalformedJwtException`() {
        val token = invalidToken()

        assertFailsWith<MalformedJwtException> {
            tested.getSubject(token.token)
        }
    }


    private fun expiredToken(): Token {
        `when`(clock.millis()).thenReturn(Instant.now().minusMillis(TokenService.ttlTokenMillis.toLong()).toEpochMilli())
        return tested.create(1)
    }

    private fun validToken(userId: Long = 1): Token {
        `when`(clock.millis()).thenReturn(Instant.now().toEpochMilli())
        return tested.create(1)
    }

    private fun invalidToken() = Token(token = "invalid_token", userId = 1)

}