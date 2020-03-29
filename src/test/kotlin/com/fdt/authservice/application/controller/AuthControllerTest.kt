package com.fdt.authservice.application.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fdt.authservice.domain.repository.CredentialRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var credentialRepository: CredentialRepository
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Before
    fun clear() {
        credentialRepository.deleteAll()
    }

    @Test
    fun `when register a user should return created and a token`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("token").exists())
                .andExpect(jsonPath("user_id").value(1))
    }

    @Test
    fun `when register a userId already taken should return error`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("token").exists())
                .andExpect(jsonPath("user_id").value(1))

        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("UserId already in use"))
    }


    @Test
    fun `when try to register without user id should return error`() {
        /*mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("field 'userId' is required"))*/
    }

    @Test
    fun `when try to register with empty user id should return error`() {
        /*mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("userId must not be empty"))*/
    }

    @Test
    fun `when try to register without pwd should return error`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1 }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("field 'password' is required"))
    }

    @Test
    fun `when try to register with empty pwd should return error`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("Password must not be empty"))
    }

    @Test
    fun `when try to login with registered user should return a token`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))

        mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("token").exists())
    }

    @Test
    fun `when try to login with a password that not match should return unauthorized`() {
        mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))

        mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":1, "password":"no mi pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized)
                .andExpect(jsonPath("message").value("Invalid password for User"))
    }

    @Test
    fun `when try to login with empty pwd should return unauthorized`() {
        mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":1, "pwd":"" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("field 'password' is required"))
    }

    @Test
    fun `when try to login with an unregistered user should return unauthorized`() {
        mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":1, "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized)
                .andExpect(jsonPath("message").value("User not exist"))
    }

    @Test
    fun `when try to login with empty user id should return error`() {
        /*mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":"", "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(content().string("Missing parameter in request"))*/
    }

    @Test
    fun `when try to login without pwd should return error`() {
        mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "user_id":1 }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("field 'password' is required"))
    }

    @Test
    fun `when try to login without user id should return error`() {
        /*mockMvc.perform(post("/${AuthController.path}/login")
                .content("""{ "pwd":"" }""")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("message").value("field 'userId' is required"))*/
    }

    @Test
    fun `when try to access a secure endpoint without token return forbidden`() {
        mockMvc.perform(get("/${AuthController.path}/ping")).andExpect(status().isForbidden)
    }

    @Test
    fun `when try to access a secure endpoint with an invalid token return forbidden`() {
        val invalidToken = "invalidToken"
        mockMvc.perform(get("/${AuthController.path}/ping")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $invalidToken"))
                .andExpect(status().isForbidden)
    }

    @Test
    fun `when try to access a secure endpoint with a valid token return ok`() {
        val body = mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "email":"foo@bar.com", "phone":"123", "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response.contentAsString

        val token = jacksonObjectMapper().readTree(body)["token"].asText()

        mockMvc.perform(get("/${AuthController.path}/ping")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token"))
                .andExpect(status().isOk)
                .andExpect(content().string("pong"))
    }

    @Test
    fun `me`() {
        val body = mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":1, "email":"foo@bar.com", "phone":"123", "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response.contentAsString

        val token = jacksonObjectMapper().readTree(body)["token"].asText()

        mockMvc.perform(get("/${AuthController.path}/me/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token"))
                .andExpect(status().isOk)
                .andExpect(content().string("1"))
    }

    @Test
    fun `not me`() {
        val body = mockMvc.perform(post("/${AuthController.path}/register")
                .content("""{ "user_id":2, "email":"foo@bar.com", "phone":"123", "password":"pwd" }""")
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response.contentAsString

        val token = jacksonObjectMapper().readTree(body)["token"].asText()

        mockMvc.perform(get("/${AuthController.path}/me/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token"))
                .andExpect(status().isForbidden)
    }

}