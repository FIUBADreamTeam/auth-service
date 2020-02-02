package com.fdt.authservice.application.controller

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.service.AuthService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/auth"])
class AuthController(private val authService: AuthService) {

    @PostMapping(path = ["/register"])
    fun register(@RequestBody credential: Credential): ResponseEntity<Token> {
        return ResponseEntity.status(CREATED).body(authService.register(credential))
    }
}