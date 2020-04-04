package com.fdt.authservice.application.dto

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.application.dto.rules.CredentialRule

class CredentialFactory(private val rules: List<CredentialRule>) {

    fun create(credentialDto: CredentialDto): Credential {
        evaluateRules(credentialDto)
        return fromDto(credentialDto)
    }

    private fun evaluateRules(credentialDto: CredentialDto) =
            rules.map { it.evaluate(credentialDto) }

    private fun fromDto(credentialDto: CredentialDto): Credential =
            Credential(userId = credentialDto.userId!!, password = credentialDto.password!!)

}