package com.ivo.book_network.test

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/test")
class TestController(
    private val testService: TestService
) {

    @GetMapping
    fun testGetter(authentication: Authentication): String {
        return testService.authenticatedUser(authentication)
    }

    @GetMapping("/userDetails")
    suspend fun getProfile(@AuthenticationPrincipal userDetails: UserDetails): String {
        val username = userDetails.username // Accessing the authenticated user's username
        return "Authenticated user: $username"
    }
}