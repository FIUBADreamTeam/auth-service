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
        val credential = givenAnyCredential()

        val token = tested.register(credential)

        assertEquals(
                credential.userId.toString(),
                getJwtSubject(token)
        )
    }

    @Test
    fun `when try to login with email and phone at the same time should throw and exception`() {
        val loginCredential = LoginCredential("foo@bar.com", "123", "pwd")

        assertFailsWith<InvalidLoginCredential> {
            tested.login(loginCredential)
        }
    }

    @Test
    fun `when try to login without email and phone should throw and exception`() {
        val loginCredential = LoginCredential("foo@bar.com", "123", "pwd")

        assertFailsWith<InvalidLoginCredential> {
            tested.login(loginCredential)
        }
    }

    @Test
    fun `when try to login with a phone that is not registered should throw and exception`() {
        val loginCredential = LoginCredential("foo@bar.com", "", "pwd")

        assertFailsWith<InvalidUser> {
            tested.login(loginCredential)
        }
    }

    @Test
    fun `when try to login with a password that not match should throw and exception`() {
        val credential = givenAnyRegisteredCredential()
        val loginCredential = toLoginCredential(credential).copy(password = "invalid_pwd")

        assertFailsWith<InvalidPassword> {
            tested.login(loginCredential)
        }
    }

    @Test
    fun `a successful login should return a valid token`() {
        val credential = givenAnyRegisteredCredential()
        val loginCredential = toLoginCredential(credential)

        val token = tested.login(loginCredential)

        assertEquals(
                credential.userId.toString(),
                getJwtSubject(token)
        )
    }


    private fun givenAnyCredential() =
            Credential(0L, 1L, "foo@bar.com", "123", "pwd")

    private fun givenAnyRegisteredCredential() =
            givenAnyCredential().also { tested.register(it) }

    private fun toLoginCredential(credential: Credential) =
            LoginCredential(credential.email, "", credential.password)

    private fun getJwtSubject(token: Token) =
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.token).body.subject
}