package com.fdt.authservice.infrastructure.repository

import com.fdt.authservice.domain.entity.Credential
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CredentialCrudRepository : CrudRepository<Credential, Long> {

    fun existsByUserId(userId: Long): Boolean
    fun findByUserId(userId: Long): Credential?

}