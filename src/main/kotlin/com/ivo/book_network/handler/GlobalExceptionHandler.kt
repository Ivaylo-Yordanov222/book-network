package com.ivo.book_network.handler

import com.ivo.book_network.handler.exception.*
import jakarta.mail.MessagingException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebExchange

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: UserNotFoundException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(RoleNotFoundException::class)
    fun handleRoleNotFoundException(
        ex: RoleNotFoundException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(TokenNotValidException::class)
    fun handleTokenNotValidException(
        ex: TokenNotValidException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(AuthenticationValidationException::class)
    fun handleAuthenticationValidationException(
        ex: AuthenticationValidationException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(UserExistsException::class)
    fun handleUserExistsException(
        ex: UserExistsException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.CONFLICT
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(MessagingException::class)
    fun handleMessagingException(
        ex: MessagingException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.INTERNAL_SERVER_ERROR
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleMessagingException(
        ex: BadCredentialsException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val response = ErrorResponse(status.value(), ex.message!!)
        return ResponseEntity(response, status)
    }

    @ExceptionHandler(ValidationErrorsException::class)
    fun handleValidationErrorsException(
        ex: ValidationErrorsException,
        exchange: ServerWebExchange
    ): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST
        val response = ErrorResponse(status.value(), ex.message!!, ex.errors)
        return ResponseEntity(response, status)
    }
}