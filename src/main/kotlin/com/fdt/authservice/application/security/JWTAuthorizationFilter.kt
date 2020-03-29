package com.fdt.authservice.application.security

import com.fdt.authservice.domain.service.TokenService
import io.jsonwebtoken.*
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizationFilter : OncePerRequestFilter() {

    companion object {
        private const val PREFIX = "Bearer "
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (existsJWTToken(request, response)) {
                val claims = validateToken(request)
                setUpSpringAuthentication(claims)
            }
            chain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        } catch (e: UnsupportedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        } catch (e: MalformedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        }
    }

    private fun validateToken(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(PREFIX, "")
        return Jwts.parserBuilder().setSigningKey(TokenService.keyPair.public).build().parseClaimsJws(jwtToken).body
    }

    private fun existsJWTToken(request: HttpServletRequest, res: HttpServletResponse): Boolean {
        val authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return !(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
    }

    private fun setUpSpringAuthentication(claims: Claims) {
        val auth = UsernamePasswordAuthenticationToken(claims.subject, null, listOf())
        SecurityContextHolder.getContext().authentication = auth
    }

}