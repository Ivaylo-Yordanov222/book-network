package com.ivo.book_network.auth.dto

data class LoginRequest(
    val email: String,
    val password: String
)