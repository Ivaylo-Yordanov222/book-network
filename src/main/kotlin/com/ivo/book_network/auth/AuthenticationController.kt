package com.ivo.book_network.auth

import com.ivo.book_network.auth.dto.RegistrationRequest
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
class AuthenticationController(private val authService: AuthenticationService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun register(@Valid @RequestBody request: RegistrationRequest): ResponseEntity<Any> {

        authService.register(request)
        return ResponseEntity.accepted().build()
    }

    @GetMapping("bla")
    fun bla(): String {
        return "basimu"
    }
}