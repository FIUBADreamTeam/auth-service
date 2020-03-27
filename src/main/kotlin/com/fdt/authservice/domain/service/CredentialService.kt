package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.repository.CredentialRepository
import org.springframework.stereotype.Service

@Service
class CredentialService(private var credentialRepository: CredentialRepository) {

    fun create(credential: Credential): Credential {
        return credentialRepository.save(credential)
    }

    fun findByEmailOrPhone(email:String, phone:String): Credential? {
        return credentialRepository.findByEmailOrPhone(email, phone)
    }
}