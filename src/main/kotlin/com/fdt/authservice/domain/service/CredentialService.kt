package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.LoginCredential
import com.fdt.authservice.domain.repository.CredentialRepository
import org.springframework.stereotype.Service

@Service
class CredentialService(private var credentialRepository: CredentialRepository) {

    fun create(credential: Credential): Credential {
        return credentialRepository.save(credential)
    }

    fun findUser(loginCredential: LoginCredential): Credential? {
        return credentialRepository.findByEmailOrPhone(loginCredential.email, loginCredential.phone)
    }

    fun existsUser(credential: Credential): Boolean{
        return credentialRepository.existsByEmailOrPhone(credential.email, credential.phone)
    }
}