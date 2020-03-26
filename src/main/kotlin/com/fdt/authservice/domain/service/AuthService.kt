package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.LoginCredential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.exception.InvalidLoginCredential
import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val credentialService: CredentialService,
        private val tokenService: TokenService
) {

    //TODO este metodo debería tener transactionl
    fun register(credential: Credential): Token {
        val credentialSaved = credentialService.create(credential)
        return tokenService.create(credentialSaved.userId)
    }

    fun findUser(loginCredential: LoginCredential): Token {
        validLoginCredential(loginCredential)
        val user = credentialService.findUser(loginCredential)
        user?.let {
            if (it.checkPassword(loginCredential.password)) {
                return tokenService.create(it.userId)
            } else {
                throw InvalidPassword("Invalid Password for User")
            }
        } ?: throw InvalidUser("User not exist")
    }

    fun validLoginCredential(loginCredential: LoginCredential) {
        // Debe tener nombre o mail pero no los dos y un pwd.
        if (!loginCredential.phone.isNullOrEmpty() && !loginCredential.email.isNullOrEmpty()){
            throw InvalidLoginCredential("Mail and Phone shouldn't be filled at the same time")
        }
        if (loginCredential.phone.isNullOrEmpty() && loginCredential.email.isNullOrEmpty()){
            throw InvalidLoginCredential("Mail and Phone shouldn't be empty at the same time")
        }

    }
}