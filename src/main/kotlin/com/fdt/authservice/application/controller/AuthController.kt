package com.fdt.authservice.application.controller

import com.fdt.authservice.application.dto.CredentialDto
import com.fdt.authservice.application.dto.CredentialFactory
import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.service.AuthService

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = [AuthController.path])
class AuthController(
        private val authService: AuthService,
        private val userFactory: CredentialFactory
) {

    companion object {
        const val path = "auth"
    }

    @PostMapping(path = ["/register"])
    fun register(@RequestBody credentialDto: CredentialDto): ResponseEntity<Token> {
        val token = authService.saveAuthInfo(userFactory.create(credentialDto))
        return ResponseEntity.status(CREATED).body(token)
    }

    @PostMapping(path = ["/login"])
    fun login(@RequestBody credentialDto: CredentialDto): Token {
        return authService.login(userFactory.create(credentialDto))
    }

    @GetMapping("/ping")
    fun ping() = "pong"

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/me/{id}")
    fun me(@PathVariable id: String) = id
}