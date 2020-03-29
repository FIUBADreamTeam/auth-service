package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.repository.CredentialRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


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