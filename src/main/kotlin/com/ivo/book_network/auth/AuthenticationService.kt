package com.ivo.book_network.auth

import com.ivo.book_network.auth.dto.LoginRequest
import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.role.RoleRepository
import com.ivo.book_network.security.JWTService
import com.ivo.book_network.security.dto.TokenResponse
import com.ivo.book_network.user.Token
import com.ivo.book_network.user.TokenRepository
import com.ivo.book_network.user.User
import com.ivo.book_network.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AuthenticationService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService
) {

    fun register(request: RegistrationRequest) {
        val userRole = roleRepository.findByName("USER")
            ?: throw IllegalStateException("Role was not found")

        val user = User(
            id = null,
            firstname = request.firstname,
            lastname = request.lastname,
            dateOfBirth = LocalDate.of(2001, 11, 21),
            email = request.email,
            password = passwordEncoder.encode(request.password),
            accountLocked = false,
            enabled = false,
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now(),
            roles = listOf(userRole)
        )
        userRepository.save(user)
        sendValidationEmail(user)
    }

    fun login(loginRequest: LoginRequest): TokenResponse {
        val dbUser = userRepository.findByEmail(loginRequest.email)
            ?: throw IllegalStateException("Incorrect email or password")
        if (!passwordEncoder.matches(loginRequest.password, dbUser.password)) {
            throw IllegalStateException("Incorrect email or password")
        }

        return jwtService.generateToken(dbUser.username, dbUser.authorities)
    }

    private fun sendValidationEmail(user: User) {
        val token = generateAndSaveActivationToken(user)

    }

    private fun generateAndSaveActivationToken(user: User): String {
        val generatedToken = generateActivationCode()
        val token = Token(
            id = null,
            token = generatedToken,
            createdAt = LocalDateTime.now(),
            expiresAt = LocalDateTime.now().plusMinutes(15),
            validatedAt = null,
            user = user
        )
        tokenRepository.save(token)
        return generatedToken
    }

    private fun generateActivationCode(): String {
        val characters = "0123456789"
        val activationCodeBuilder: StringBuilder = StringBuilder()
        val secureRandom = SecureRandom()
        for (i in 0 until 6) {
            val randomIndex = secureRandom.nextInt(characters.length)
            activationCodeBuilder.append(characters[randomIndex])
        }
        return activationCodeBuilder.toString()
    }
}
