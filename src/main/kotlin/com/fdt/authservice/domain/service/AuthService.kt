package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.Token
import org.springframework.stereotype.Service

@Service
class AuthService(private val credentialService: CredentialService, private val tokenService: TokenService) {

    //TODO este metodo debería tener transactionl
    fun register(credential: Credential): Token {
        val credentialSaved = credentialService.create(credential)
        return tokenService.create(credentialSaved.userId)
    }
}