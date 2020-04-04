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
import kotlin.test.*

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
        val credential = givenAnyCredential()

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

    @Test
    fun `when user id exists return true`() {
        val credential = givenAnySavedCredential()
        assertTrue(tested.existsUserId(credential))
    }

    @Test
    fun `when user id not exists return false`() {
        val credential = givenAnyCredential()
        assertFalse(tested.existsUserId(credential))
    }

    @Test
    fun `when find by user id and exits should return the credentials`() {
        val credential = givenAnySavedCredential()

        val saved = tested.findByUserId(credential.userId)!!

        assertEquals(credential.id, saved.id)
        assertEquals(credential.userId, saved.userId)
        assertNotNull(saved.password)
        assertTrue(saved.password.isNotEmpty())
    }

    @Test
    fun `when find by user id and not exits should return null`() {
        val saved = tested.findByUserId(0)
        assertNull(saved)
    }


    private fun givenAnyCredential(password: String = "pwd") =
            Credential(0L, 1L, password)

    private fun givenAnySavedCredential() =
            givenAnyCredential().also { tested.create(it) }
}