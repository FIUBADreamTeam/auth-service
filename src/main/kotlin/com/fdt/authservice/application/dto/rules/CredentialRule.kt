package com.fdt.authservice.application.dto.rules

import com.fdt.authservice.application.dto.CredentialDto

interface CredentialRule {
    fun evaluate(toEvaluate: CredentialDto)
}