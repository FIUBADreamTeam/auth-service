package com.fdt.authservice.infrastructure.repository

import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.repository.TokenRepository
import org.springframework.stereotype.Component

@Component
class TokenRepositoryImpl : TokenRepository {

    override fun create(token: Token): Token {
        return token
    }
}