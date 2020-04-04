package com.fdt.authservice.application.dto.rules

import com.fdt.authservice.application.dto.CredentialDto
import com.fdt.authservice.application.exception.NullUserId

class NotNullUserIdRule : CredentialRule {
    override fun evaluate(toEvaluate: CredentialDto) {
        if(toEvaluate.userId == null) {
                 throw NullUserId("UserId must not be null")
        }
    }
}