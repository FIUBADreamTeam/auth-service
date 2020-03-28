package com.fdt.authservice.domain.entity

data class LoginCredential (
        val email: String,
        val phone: String,
        val password: String
)