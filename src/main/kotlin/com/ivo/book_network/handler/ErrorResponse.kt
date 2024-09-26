package com.ivo.book_network.handler

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    var validationErrorMessages: List<String>? = null
)
