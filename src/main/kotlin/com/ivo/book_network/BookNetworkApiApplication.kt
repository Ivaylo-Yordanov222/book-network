package com.ivo.book_network

import com.ivo.book_network.role.Role
import com.ivo.book_network.role.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalDateTime

@SpringBootApplication
@EnableJpaAuditing
class BookNetworkApiApplication(
	private val roleRepository: RoleRepository
) :CommandLineRunner {
	override fun run(vararg args: String?) {
		roleRepository.save(Role(1, "USER", LocalDateTime.now(), null,null))
	}
}

fun main(args: Array<String>) {
	runApplication<BookNetworkApiApplication>(*args)
}
