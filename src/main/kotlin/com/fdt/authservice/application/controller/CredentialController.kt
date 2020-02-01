package com.fdt.authservice.application.controller

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.infrastructure.repository.CredentialRepositoryImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/credentials"])
class CredentialController {

    @Autowired
    lateinit var credentialRepositoryImpl: CredentialRepositoryImpl

    @PostMapping
    fun create(@RequestBody credential: Credential): Credential {
        return credentialRepositoryImpl.save(credential)
    }
}