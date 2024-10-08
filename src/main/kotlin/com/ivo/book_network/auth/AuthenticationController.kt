package com.ivo.book_network.auth

import com.ivo.book_network.auth.dto.LoginRequest
import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.security.dto.TokenResponse
import com.ivo.book_network.validators.LoginRequestValidator
import com.ivo.book_network.validators.RegistrationRequestValidator
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
class AuthenticationController(private val authService: AuthenticationService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun register(@RequestBody request: RegistrationRequest): ResponseEntity<Any> {
        RegistrationRequestValidator.validate(request)
        authService.register(request)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        LoginRequestValidator.validate(request)
        return ResponseEntity.ok(authService.login(request))
    }

    @GetMapping("/activate-account")
    fun activate(@RequestParam token:String) {
        authService.activateAccount(token)
    }
}