package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(private val tokenRepository: TokenRepository) {

    fun create(userId: Long): Token {
        //TODO ver como generar el hash del token
        val token = Token(token = "asd", userId = userId)
        return tokenRepository.create(token)
    }
}