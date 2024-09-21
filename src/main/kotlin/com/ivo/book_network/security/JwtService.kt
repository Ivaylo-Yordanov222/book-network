package com.ivo.book_network.security

import com.ivo.book_network.security.dto.TokenResponse
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey


@Service
class JWTService(
    @Value("\${application.security.jwt.secret-key}") private val secret: String
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()


    fun generateToken(username: String, authorities: Collection<GrantedAuthority>): TokenResponse {
        val roles = authorities.map { it.authority } // Convert authorities to a list of role names
        val claims = mapOf("roles" to roles) // Create a claims map with the roles
        val expirationTime = Instant.now().plus(10, ChronoUnit.DAYS)

        val builder: JwtBuilder = Jwts.builder()
            .setSubject(username)
            .addClaims(claims)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(expirationTime))
            .signWith(key)

        return TokenResponse(
            accessToken = builder.compact(),
            expirationTime = expirationTime.toEpochMilli()
        )
    }

    fun validateToken(user: UserDetails, token: String): Boolean {
        val claims: Claims = parser.parseClaimsJws(token).body
        val unExpired: Boolean = claims.expiration.after(Date.from(Instant.now()))

        return unExpired && user.username == claims.subject
    }

    fun isValid(token: String?): Boolean {
        try {
            parser.parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            return false
        }
    }

    fun getUsername(token: String): String {
        return parser.parseClaimsJws(token).body.subject
    }
}