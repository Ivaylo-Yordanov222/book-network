package com.ivo.book_network.validators

import com.ivo.book_network.auth.dto.RegistrationRequest
import com.ivo.book_network.handler.exception.ValidationErrorsException
import com.ivo.book_network.validators.BaseValidator.Companion.isEmptyField
import com.ivo.book_network.validators.BaseValidator.Companion.validateEmail
import com.ivo.book_network.validators.BaseValidator.Companion.validatePassword
import java.util.regex.Pattern

class RegistrationRequestValidator {

    companion object {
        fun validate(request: RegistrationRequest) {
            val errors: ArrayList<String> = arrayListOf()
            validateName(request.firstname, errors)
            validateName(request.lastname, errors)
            validateEmail(request.email, errors)
            validatePassword(request.password, errors)

            if (errors.size > 0) {
                throw ValidationErrorsException(errors)
            }
        }

        private fun validateName(name: String, errors: ArrayList<String>) {
            if (!isEmptyField(name, errors)) {
                val pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_\\-.]{2,50}$")
                val matcher = pattern.matcher(name)
                if (!matcher.matches()) {
                    errors.add("Field must contain only letters, numbers _ and -")
                }
            }
        }




        private fun validateConfirmPassword(name: String) {

        }
    }
}