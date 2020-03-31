package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Clock
import java.util.*

@Service
class TokenService(
        private val tokenRepository: TokenRepository,
        private val clock: Clock
) {

    companion object {
        private val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)
        const val ttlTokenMillis = 15 * 60 * 1000
    }

    fun create(userId: Long): Token {
        return Token(token = getJWTToken(userId.toString()), userId = userId)
    }

    fun getSubject(token: String): String =
            Jwts.parserBuilder()
                    .setSigningKey(keyPair.public)
                    .build()
                    .parseClaimsJws(token)
                    .body
                    .subject

    private fun getJWTToken(username: String): String {
        return Jwts
                .builder()
                .setSubject(username)
                .setExpiration(Date(clock.millis() + ttlTokenMillis))
                .signWith(keyPair.private).compact()
    }
}