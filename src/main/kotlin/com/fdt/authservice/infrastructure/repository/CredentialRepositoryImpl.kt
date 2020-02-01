package com.fdt.authservice.infrastructure.repository

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.repository.CredentialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CredentialRepositoryImpl : CredentialRepository {

    @Autowired
    private lateinit var relationalCredentialRepository: CredentialCrudRepository

    override fun save(credential: Credential): Credential {
        return relationalCredentialRepository.save(credential)
    }

}