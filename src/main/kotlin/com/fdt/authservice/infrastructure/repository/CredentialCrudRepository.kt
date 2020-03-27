package com.fdt.authservice.infrastructure.repository

import com.fdt.authservice.domain.entity.Credential
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CredentialCrudRepository : CrudRepository<Credential, Long> {

    fun findByEmailOrPhone(email: String?, phone: String?): Credential?
    fun existsByEmailOrPhone(email: String, phone: String): Boolean

}