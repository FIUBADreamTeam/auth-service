package com.fdt.authservice.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "credential")
data class Credential(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long,
        val userId: Long,
        val email: String,
        val phone: String,
        var password: String
){
        fun checkPassword(pwd:String) = this.password==pwd
}