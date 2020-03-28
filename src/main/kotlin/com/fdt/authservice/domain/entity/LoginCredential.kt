package com.fdt.authservice.domain.entity

data class LoginCredential (
        val email: String,
        val phone: String,
        var password: String
)