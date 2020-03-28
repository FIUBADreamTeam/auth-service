package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key

@Service
class TokenService(private val tokenRepository: TokenRepository) {

    companion object {
        val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    }

    fun create(userId: Long): Token {
        //TODO ver como generar el hash del token
        val token = Token(token = getJWTToken(userId.toString()), userId = userId)
        return tokenRepository.save(token)
    }

    private fun getJWTToken(username: String): String {
        return Jwts
                .builder()
                .setSubject(username)
                .signWith(key).compact()
    }
}