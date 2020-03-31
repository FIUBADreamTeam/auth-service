package com.fdt.authservice

import com.fdt.authservice.application.security.JWTAuthorizationFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthServiceApplication>(*args)
}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
internal class WebSecurityConfig(private val filter: JWTAuthorizationFilter) : WebSecurityConfigurerAdapter() {
	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http.csrf().disable()
				.addFilterAfter(filter, UsernamePasswordAuthenticationFilter::class.java)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/register").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.anyRequest().authenticated()
	}
}
