package com.ivo.book_network.auth

import com.ivo.book_network.auth.dto.LoginRequest
import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.email.EmailService
import com.ivo.book_network.email.EmailTemplateName
import com.ivo.book_network.role.Role
import com.ivo.book_network.role.RoleRepository
import com.ivo.book_network.role.RoleType
import com.ivo.book_network.security.JWTService
import com.ivo.book_network.security.dto.TokenResponse
import com.ivo.book_network.user.Token
import com.ivo.book_network.user.TokenRepository
import com.ivo.book_network.user.User
import com.ivo.book_network.user.UserRepository
import jakarta.mail.MessagingException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class AuthenticationService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val emailService: EmailService,
    @Value("\${application.mailing.frontend.activation-url}") private val activationUrl: String
) {
    companion object {
        private const val ACTIVATION_CODE_LENGTH = 6
        private const val ACTIVATION_CODE_CHARACTERS = "0123456789"
    }

    fun register(request: RegistrationRequest) {
        val role = roleRepository.findByName(RoleType.USER.name)
            ?: throw IllegalStateException("Role was not found")

        val user = toUser(request, role)
        userRepository.save(user)
        sendValidationEmail(user)
    }

    fun login(loginRequest: LoginRequest): TokenResponse {
        val dbUser = userRepository.findByEmail(loginRequest.email)
            ?: throw IllegalStateException("Incorrect email or password")

        if (!dbUser.isEnabled) {
            throw IllegalStateException("User is not active")
        }

        if (!passwordEncoder.matches(loginRequest.password, dbUser.password)) {
            throw IllegalStateException("Incorrect email or password")
        }

        return jwtService.generateToken(dbUser.username, dbUser.authorities)
    }

    @Throws(MessagingException::class)
    private fun sendValidationEmail(user: User) {
        val token = generateAndSaveActivationToken(user)
        emailService.sendEmail(
            user.username,
            user.fullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl,
            token,
            "Account activation"
        )
    }

    private fun generateAndSaveActivationToken(user: User): String {
        val generatedToken = generateActivationCode()
        val token = Token(
            id = null,
            token = generatedToken,
            createdAt = Instant.now(),
            expiresAt = Instant.now().plus(15, ChronoUnit.MINUTES),
            validatedAt = null,
            user = user
        )
        tokenRepository.save(token)
        return generatedToken
    }

    private fun generateActivationCode(): String {
        val activationCodeBuilder: StringBuilder = StringBuilder()
        val secureRandom = SecureRandom()
        for (i in 0 until ACTIVATION_CODE_LENGTH) {
            val randomIndex = secureRandom.nextInt(ACTIVATION_CODE_CHARACTERS.length)
            activationCodeBuilder.append(ACTIVATION_CODE_CHARACTERS[randomIndex])
        }
        return activationCodeBuilder.toString()
    }

    private fun toUser(request: RegistrationRequest, role: Role) = User(
        id = null,
        firstname = request.firstname,
        lastname = request.lastname,
        email = request.email,
        password = passwordEncoder.encode(request.password),
        createdDate = LocalDateTime.now(),
        lastModifiedDate = LocalDateTime.now(),
        roles = listOf(role)
    )

    fun activateAccount(token: String) {
        val existingToken = tokenRepository.findByToken(token) ?: throw IllegalStateException("Token is not valid")

        if (Instant.now().isAfter(existingToken.expiresAt)) {
            sendValidationEmail(existingToken.user)
            throw IllegalStateException("Activation token has expired. A new token has been sent to your email")
        }

        existingToken.user.isEnabled = true
        userRepository.save(existingToken.user)
        existingToken.setValidatedAt(Instant.now())
        tokenRepository.save(existingToken)
    }
}
