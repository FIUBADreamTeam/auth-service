package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.repository.CredentialRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CredentialService(
        private var credentialRepository: CredentialRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun create(credential: Credential): Credential {
        credential.password = passwordEncoder.encode(credential.password)
        return credentialRepository.save(credential)
    }

    fun findByEmailOrPhone(email:String, phone:String): Credential? {
        return credentialRepository.findByEmailOrPhone(email, phone)
    }

    fun exists(credential: Credential): Boolean{
        return credentialRepository.existsByEmailOrPhone(credential.email, credential.phone)
    }
}