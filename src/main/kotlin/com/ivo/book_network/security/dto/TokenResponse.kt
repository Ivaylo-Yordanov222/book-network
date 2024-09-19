package com.ivo.book_network.security.dto

data class TokenResponse(
    val accessToken: String,
    val expirationTime: Long
)
