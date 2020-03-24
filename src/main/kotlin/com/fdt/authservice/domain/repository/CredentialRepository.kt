package com.fdt.authservice.domain.repository

import com.fdt.authservice.domain.entity.Credential

interface CredentialRepository {
    fun save(credential: Credential): Credential

    fun deleteAll()
}