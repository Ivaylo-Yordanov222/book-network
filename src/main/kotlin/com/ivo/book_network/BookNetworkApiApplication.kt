package com.ivo.book_network

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BookNetworkApiApplication

fun main(args: Array<String>) {
	runApplication<BookNetworkApiApplication>(*args)
}
