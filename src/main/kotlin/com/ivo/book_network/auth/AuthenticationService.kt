package com.ivo.book_network.auth

import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.role.RoleRepository
import com.ivo.book_network.user.User
import com.ivo.book_network.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AuthenticationService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
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
    }
}
