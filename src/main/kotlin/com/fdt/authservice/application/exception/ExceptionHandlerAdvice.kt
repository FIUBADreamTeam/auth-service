package com.fdt.authservice.application.exception

import com.fdt.authservice.domain.exception.InvalidPassword
import com.fdt.authservice.domain.exception.InvalidUser
import com.fdt.authservice.domain.exception.UnavailableUserId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(InvalidPassword::class)
    fun handleInvalidPassword(ex: InvalidPassword) =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("""{"message":"${ex.message}"}""")

    @ExceptionHandler(InvalidUser::class)
    fun handleInvalidUser(ex: InvalidUser) =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("""{"message":"${ex.message}"}""")

    @ExceptionHandler(UnavailableUserId::class)
    fun handleUnavailableUserId(ex: UnavailableUserId) =
            ResponseEntity.status(HttpStatus.CONFLICT).body("""{"message":"${ex.message}"}""")

    @ExceptionHandler(NullOrEmptyPassword::class)
    fun handleEmptyPassword(ex: NullOrEmptyPassword) =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("""{"message":"${ex.message}"}""")

    @ExceptionHandler(NullUserId::class)
    fun handleNullUserId(ex: NullUserId) =
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("""{"message":"${ex.message}"}""")

}
