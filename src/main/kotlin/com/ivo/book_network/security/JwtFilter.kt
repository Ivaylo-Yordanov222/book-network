package com.ivo.book_network.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsServiceImpl
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authorizationHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        if (exchange.request.path.value().contains("/api/v1/auth")) {
            return chain.filter(exchange)
        }

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }

        val jwt = authorizationHeader.substring(7)
        val userEmail = jwtService.extractUsername(jwt)

        return Mono.justOrEmpty(userEmail)
            .flatMap {
                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                        )
                        val securityContext = SecurityContextImpl(authToken)
                        ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext))
                    }
                }
                Mono.empty<Void>()
            }.then(chain.filter(exchange))
    }
}
