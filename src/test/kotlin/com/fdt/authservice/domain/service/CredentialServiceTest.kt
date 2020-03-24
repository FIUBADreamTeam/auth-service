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
        assertEquals(credential.email, saved.email)
        assertEquals(credential.phone, saved.phone)
        assertEquals(credential.password, saved.password)
    }

    private fun givenAnyCredential() =
            Credential(0L, 1L, "foo@bar.com", "123", "pwd")
}