package com.ivo.book_network.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthManager(
    private val jwtService: JWTService,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .cast(BearerToken::class.java)
            .flatMap { auth ->
                // Try to extract the username from the token
                val token = auth.credentials
                try {
                    val username = jwtService.getUsername(token)

                    // Load user details based on the extracted username
                    Mono.justOrEmpty(userDetailsServiceImpl.loadUserByUsername(username))
                        .flatMap { userDetails ->
                            // Validate the token using userDetails and token
                            if (jwtService.validateToken(userDetails, token)) {
                                Mono.just(
                                    UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.authorities
                                    )
                                )
                            } else {
                                Mono.error<Authentication>(BadCredentialsException("Invalid token"))
                            }
                        }
                } catch (e: Exception) {
                    // Handle any exception during token parsing (invalid token, etc.)
                    Mono.error(BadCredentialsException("Invalid token format or parsing error"))
                }
            }
    }

}
