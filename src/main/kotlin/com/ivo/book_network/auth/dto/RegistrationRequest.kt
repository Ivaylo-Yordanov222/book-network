package com.ivo.book_network.auth.dto

data class RegistrationRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
)