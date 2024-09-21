package com.ivo.book_network.user

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
data class Token(
    @Id
    @GeneratedValue
    private val id: Int?,
    private val token: String,
    private val createdAt: Instant,
    val expiresAt: Instant,
    private var validatedAt: Instant?,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User
) {
    fun setValidatedAt(dateTime: Instant) {
        this.validatedAt = dateTime
    }
}
