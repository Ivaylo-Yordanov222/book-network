package com.ivo.book_network.test

import com.ivo.book_network.user.User
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class TestService {

    fun authenticatedUser(authentication: Authentication): String {
        val user = authentication.principal as User

        return user.fullName()
    }
}