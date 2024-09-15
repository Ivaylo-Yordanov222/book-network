package com.ivo.book_network.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Token, Int> {

    fun findByToken(token: String): Token?
}