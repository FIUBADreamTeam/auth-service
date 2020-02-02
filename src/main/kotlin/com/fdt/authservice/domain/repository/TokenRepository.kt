package com.fdt.authservice.domain.repository

import com.fdt.authservice.domain.entity.Token

interface TokenRepository {
    fun save(token: Token): Token
}