package com.fdt.authservice.domain.entity

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CredentialTest {

    @Test
    fun `when check password with the same password should return true`() {
        val pwd = "pwd"
        val credential = Credential(1L, 1L, "foo@bar.com", "123", pwd)
        assertTrue(credential.checkPassword(pwd))
    }

    @Test
    fun `when check password with the another password should return false`() {
        val pwd = "pwd"
        val credential = Credential(1L, 1L, "foo@bar.com", "123", pwd)
        assertFalse(credential.checkPassword("another_pwd"))
    }
}