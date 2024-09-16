package com.ivo.book_network.security

import org.springframework.security.authentication.AuthenticationServiceException
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
                val username = jwtService.getUsername(auth.credentials)

                Mono.justOrEmpty(userDetailsServiceImpl.loadUserByUsername(username))
                    .flatMap { userDetails ->
                        if (jwtService.validateToken(userDetails, auth.credentials)) {
                            Mono.just(UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
                        } else {
                            Mono.error<Authentication>(BadCredentialsException("Invalid token"))
                        }
                    }
            }
            .onErrorResume { e ->
                if (e is AuthenticationServiceException) {
                    Mono.error(e)
                } else {
                    Mono.error(AuthenticationServiceException("Authentication failed", e))
                }
            }
    }
}
