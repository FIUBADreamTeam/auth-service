package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import com.fdt.authservice.domain.exception.UnavailableUserId
import com.fdt.authservice.domain.repository.CredentialRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


@RunWith(SpringRunner::class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private lateinit var credentialRepository: CredentialRepository
    @Autowired
    private lateinit var tokenService: TokenService
    @Autowired
    private lateinit var tested: AuthService

    @Before
    fun clear() {
        credentialRepository.deleteAll()
    }

    @Test
    fun `a successful registration should return a valid token`() {
        val credential = givenAnyCredential()
        val token = tested.saveAuthInfo(credential)
        assertEquals(credential.userId.toString(), tokenService.getSubject(token.token))
    }

    @Test
    fun `when try to register with an existing userId should throw an exception`() {
        val credential = givenAnySavedCredential()
        assertFailsWith<UnavailableUserId> {
            tested.saveAuthInfo(credential)
        }
    }

    @Test
    fun `a successful login should return a valid token`() {
        val credential = givenAnySavedCredential()
        val token = tested.login(credential)
        assertEquals("1", tokenService.getSubject(token.token))
    }

    @Test
    fun `when try to login with a not registered user id should throw an exception`() {
        val credential = givenAnyCredential()
        assertFailsWith<InvalidUser> {
            tested.login(credential)
        }
    }

    @Test
    fun `when try to login with a password that not match should throw an exception`() {
        val credential = givenAnySavedCredential()
        assertFailsWith<InvalidPassword> {
            tested.login(Credential(0, credential.userId, "not matching pwd"))
        }
    }

    private fun givenAnyCredential() =
            Credential(0, 1, "123")

    private fun givenAnySavedCredential() =
            givenAnyCredential().also { tested.saveAuthInfo(it.copy()) }
}