package com.fdt.authservice.domain.service

import com.fdt.authservice.domain.entity.Credential
import com.fdt.authservice.domain.service.TokenService.Companion.key
import io.jsonwebtoken.Jwts
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private lateinit var tested: AuthService

    @Test
    fun `can create credentials`() {
        val credential = givenAnyCredential()
        val token = tested.register(credential)

        assertEquals(
                credential.userId.toString(),
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.token).body.subject
        )
    }

    private fun givenAnyCredential() =
            Credential(0L, 1L, "foo@bar.com", "123", "pwd")
}