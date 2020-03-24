package com.fdt.authservice.application.exception

import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(InvalidPassword::class)
    fun handleInvalidPassword(ex: InvalidPassword): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("")
    }
    @ExceptionHandler(InvalidUser::class)
    fun handleInvalidPassword(ex: InvalidUser): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("")
    }

}