package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.repository.CredentialRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest
class CredentialServiceTest {

    @Autowired
    private lateinit var credentialRepository: CredentialRepository
    @Autowired
    private lateinit var tested: CredentialService

    @Before
    fun clear() {
        credentialRepository.deleteAll()
    }

    @Test
    fun `can create credentials`() {
        val rawPassword = "pwd"
        val credential = givenAnyCredential(rawPassword)

        val saved = tested.create(credential)

        assertEquals(credential.userId, saved.userId)
        assertNotNull(saved.password)
        assertTrue(saved.password.isNotEmpty())
    }

    @Test
    fun `when create credentials the password is saved encoded`() {
        val rawPassword = "pwd"
        val credential = givenAnyCredential(rawPassword)

        val saved = tested.create(credential)

        assertNotEquals(rawPassword, saved.password)
    }


    private fun givenAnyCredential(password: String = "pwd") =
            Credential(0L, 1L, password)
}