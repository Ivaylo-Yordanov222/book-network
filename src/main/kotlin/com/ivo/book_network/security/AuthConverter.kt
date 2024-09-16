package com.ivo.book_network.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(
            exchange!!.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        )
            .filter { s ->
                s.startsWith("Bearer ")
            }.map { s ->
                s.substring(7)
            }.map { s ->
                BearerToken(s)
            }
    }
}