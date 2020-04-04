package com.fdt.authservice.application.config

import com.fdt.authservice.application.dto.CredentialFactory
import com.fdt.authservice.application.dto.rules.NotNullOrEmptyPasswordRule
import com.fdt.authservice.application.dto.rules.NotNullUserIdRule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CredentialFactoryDefinition {

    @Bean
    fun credentialFactory(): CredentialFactory =
            CredentialFactory(listOf(
                    NotNullOrEmptyPasswordRule(),
                    NotNullUserIdRule()
            ))
}
