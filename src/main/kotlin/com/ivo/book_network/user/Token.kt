package com.ivo.book_network.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Token(
    @Id
    @GeneratedValue
    private val id: Int,
    private val token: String,
    private val createdAt: LocalDateTime,
    private val expiresAt: LocalDateTime,
    private val validatedAt: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private val user: User
)
