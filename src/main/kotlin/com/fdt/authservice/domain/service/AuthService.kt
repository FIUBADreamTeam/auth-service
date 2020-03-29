package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.exception.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val credentialService: CredentialService,
        private val tokenService: TokenService,
        private val passwordEncoder: PasswordEncoder
) {

    //TODO: method should be atomic?
    fun saveAuthenticationInfo(credential: Credential): Token {
        validAuthenticationInfo(credential)
        val credentialSaved = credentialService.create(credential)
        return tokenService.create(credentialSaved.userId)
    }

    fun login(loginCredential: Credential): Token {
        checkFieldsFilling(loginCredential)
        val credential = credentialService.findByUserId(loginCredential.userId)
        credential?.let {
            return if (checkPassword(loginCredential.password, it.password)) tokenService.create(it.userId)
            else throw InvalidPassword("Invalid password for User")
        } ?: throw InvalidUser("User not exist")
    }

    private fun validAuthenticationInfo(credential: Credential) {
        checkFieldsFilling(credential)
        if (credentialService.exists(credential)){
            throw UnavailableUserId("UserId already in use")
        }
    }

    private fun checkFieldsFilling(credential: Credential){
        // TODO Error Jackson convert to 0 when field is not declared
        if (credential.password.isEmpty()){
            throw EmptyPassword("Password must not be empty")
        }
    }

    private fun checkPassword(rawPassword: String, encodedPassword: String) =
            passwordEncoder.matches(rawPassword, encodedPassword)
}