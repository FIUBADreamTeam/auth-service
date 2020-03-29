package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.entity.LoginCredential
import com.fdt.authservice.domain.entity.Token
import com.fdt.authservice.domain.exception.InvalidLoginCredential
import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import com.fdt.authservice.domain.repository.CredentialRepository
import com.fdt.authservice.domain.service.TokenService.Companion.key
import io.jsonwebtoken.Jwts
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertFailsWith


@RunWith(SpringRunner::class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private lateinit var credentialRepository: CredentialRepository
    @Autowired
    private lateinit var tested: AuthService

    @Before
    fun clear() {
        credentialRepository.deleteAll()
    }

    @Test
    fun `a successful registration should return a valid token`() {
    }

    @Test
    fun `a successful login should return a valid token`() {
    }
}