package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import com.fdt.authservice.domain.exception.UnavailableUserId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val credentialService: CredentialService,
        private val tokenService: TokenService,
        private val passwordEncoder: PasswordEncoder
) {

    //TODO: method should be atomic?
    fun saveAuthInfo(credential: Credential): Token {
        if (credentialService.existsUserId(credential)) throw UnavailableUserId("UserId already exists")
        val credentialSaved = credentialService.create(credential)
        return tokenService.create(credentialSaved.userId)
    }

    fun login(loginCredential: Credential): Token {
        val credential = credentialService.findByUserId(loginCredential.userId) ?: throw InvalidUser("User not exist")
        return if (checkPassword(loginCredential.password, credential.password))
            tokenService.create(credential.userId)
        else
            throw InvalidPassword("Invalid password")
    }

    private fun checkPassword(rawPassword: String, encodedPassword: String) =
            passwordEncoder.matches(rawPassword, encodedPassword)
}