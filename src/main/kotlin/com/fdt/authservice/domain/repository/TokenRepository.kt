package com.fdt.authservice.domain.repository

import com.fdt.authservice.domain.entity.Token

interface TokenRepository {

    fun create(token: Token): Token
}