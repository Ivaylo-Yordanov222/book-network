package com.ivo.book_network.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.SecretKey

@Service
class JWTService(
    @Value("\${application.security.jwt.secret-key}") private val secret: String
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()


    fun generateToken(username: String, authorities: Collection<GrantedAuthority>): String {
        val roles = authorities.map { it.authority } // Convert authorities to a list of role names
        val claims = mapOf("roles" to roles) // Create a claims map with the roles

        val builder: JwtBuilder = Jwts.builder()
            .setSubject(username)
            .addClaims(claims) // Set the custom claims
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
            .signWith(key)

        return builder.compact()
    }

    fun validateToken(user: UserDetails, token: String): Boolean {
        val claims: Claims = parser.parseClaimsJws(token).body
        val unExpired: Boolean = claims.expiration.after(Date.from(Instant.now()))

        return unExpired && user.username == claims.subject
    }

    fun getUsername(token: String): String {
        return parser.parseClaimsJws(token).body.subject
    }
}