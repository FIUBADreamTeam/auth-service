package com.fdt.authservice.application.controller

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.LoginCredential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.service.AuthService

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = [AuthController.path])
class AuthController(private val authService: AuthService) {

    companion object {
        const val path = "auth"
    }

    @PostMapping(path = ["/register"])
    fun register(@RequestBody credential: Credential): ResponseEntity<Token> {
        val token = authService.register(credential)
        return ResponseEntity
                .status(CREATED)
                .body(token)
    }

    @PostMapping(path = ["/login"])
    fun login(@RequestBody loginCredential: LoginCredential): Token {
        return authService.login(loginCredential)
    }

    @GetMapping("/ping")
    fun ping() = "pong"

    @PreAuthorize("#id == authentication.principal")
    @GetMapping("/me/{id}")
    fun me(@PathVariable id: String) = id
}