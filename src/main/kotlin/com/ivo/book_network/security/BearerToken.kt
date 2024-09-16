package com.ivo.book_network.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

class BearerToken(private val token: String) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
    override fun getCredentials(): String {
        return this.token
    }

    override fun getPrincipal(): String {
        return this.token
    }
}