package com.ivo.book_network.auth

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/test")
class TestController {

    @GetMapping
    fun testGetter(): String {
        return "proba"
    }
}