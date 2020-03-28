package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.LoginCredential
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

    //TODO este metodo debería tener transactionl
    fun register(credential: Credential): Token {
        validRegistrationCredential(credential)
        val credentialSaved = credentialService.create(credential)
        return tokenService.create(credentialSaved.userId)
    }

    fun login(loginCredential: LoginCredential): Token {
        validLoginCredential(loginCredential)
        val credential = credentialService.findByEmailOrPhone(loginCredential.email, loginCredential.phone)
        credential?.let {
            return if (checkPassword(loginCredential.password, it.password)) tokenService.create(it.userId)
            else throw InvalidPassword("Invalid Password for User")
        } ?: throw InvalidUser("User not exist")
    }

    private fun validRegistrationCredential(credential: Credential) {
        if (credentialService.exists(credential)){
            throw AlreadyTakenMailOrPhone("Mail or Phone already taken")
        }
        if (credential.password.isNullOrEmpty()){
            throw EmptyPassword("Password must not be empty")
        }
    }

     private fun validLoginCredential(loginCredential: LoginCredential) {
        if (!loginCredential.phone.isNullOrEmpty() && !loginCredential.email.isNullOrEmpty()){
            throw InvalidLoginCredential("Mail and Phone shouldn't be filled at the same time")
        }
        if (loginCredential.phone.isNullOrEmpty() && loginCredential.email.isNullOrEmpty()){
            throw InvalidLoginCredential("Mail and Phone shouldn't be empty at the same time")
        }
    }

    private fun checkPassword(rawPassword: String, encodedPassword: String) =
            passwordEncoder.matches(rawPassword, encodedPassword)
}