package com.ivo.book_network.validators

import com.ivo.book_network.auth.dto.LoginRequest
import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.handler.exception.ValidationErrorsException
import com.ivo.book_network.validators.BaseValidator.Companion.validateEmail
import com.ivo.book_network.validators.BaseValidator.Companion.validatePassword
import java.util.regex.Pattern

class LoginRequestValidator {

    companion object {
        fun validate(request: LoginRequest) {
            val errors: ArrayList<String> = arrayListOf()
            validateEmail(request.email, errors)
            validatePassword(request.password, errors)

            if (errors.size > 0) {
                throw ValidationErrorsException(errors)
            }
        }
    }
}