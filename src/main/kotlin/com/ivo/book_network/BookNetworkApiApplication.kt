package com.ivo.book_network

import com.ivo.book_network.role.Role
import com.ivo.book_network.role.RoleRepository
import com.ivo.book_network.role.RoleType
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync
import java.time.Instant

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
class BookNetworkApiApplication(
    private val roleRepository: RoleRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        roleRepository.save(Role(1, RoleType.USER.name, Instant.now(), null, null))
        println("Test")
    }
}

fun main(args: Array<String>) {
    runApplication<BookNetworkApiApplication>(*args)
}
