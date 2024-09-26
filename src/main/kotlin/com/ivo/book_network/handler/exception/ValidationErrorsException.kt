package com.ivo.book_network.handler.exception

class ValidationErrorsException(val errors: List<String>) : RuntimeException() {
    override val message: String
        get() = "Validation errors"
}
