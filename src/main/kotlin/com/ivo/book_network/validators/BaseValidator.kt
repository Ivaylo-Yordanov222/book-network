package com.ivo.book_network.validators

import java.util.regex.Pattern

class BaseValidator {
    companion object {
        fun validateEmail(email: String, errors: ArrayList<String>) {
            if (!isEmptyField(email, errors)) {
                val pattern =
                    Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
                val matcher = pattern.matcher(email)
                if (!matcher.matches()) {
                    errors.add("Email have to be a valid email")
                }
            }
        }

        fun validatePassword(password: String, errors: ArrayList<String>) {
            if (!isEmptyField(password, errors)) {
                val pattern =
                    Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@!%*?&]{6,72}$")
                val matcher = pattern.matcher(password)
                if (!matcher.matches()) {
                    errors.add("Invalid password: it should be at least 6 characters long, contain at least one digit, one special symbol, one lowercase letter and one uppercase letter")
                }
            }
        }

        fun isEmptyField(name: String, errors: ArrayList<String>): Boolean {
            if (name.trim() == "") {
                errors.add("Field have to be not empty")
                return true
            }
            return false
        }
    }
}