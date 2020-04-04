package com.fdt.authservice.application.dto.rules

import com.fdt.authservice.application.dto.CredentialDto
import com.fdt.authservice.application.exception.NullOrEmptyPassword

class NotNullOrEmptyPasswordRule : CredentialRule {
    override fun evaluate(toEvaluate: CredentialDto) {
        if (toEvaluate.password.isNullOrEmpty()) {
            throw NullOrEmptyPassword("Password must not be null or empty")
        }
    }
}