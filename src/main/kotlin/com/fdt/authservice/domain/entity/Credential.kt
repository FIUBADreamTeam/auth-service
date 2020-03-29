package com.fdt.authservice.domain.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "credential")
data class Credential(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long = 0,
        val userId: Long,
        var password: String
)