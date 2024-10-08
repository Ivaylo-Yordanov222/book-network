package com.ivo.book_network.role

import com.fasterxml.jackson.annotation.JsonIgnore
import com.ivo.book_network.user.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Role(
    @Id
    @GeneratedValue
    private val id: Int,
    @Column(unique = true)
    val name: String,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private val createdDate: Instant,
    @LastModifiedDate
    @Column(insertable = false)
    private var lastModifiedDate: Instant? = null,
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private val users: List<User>? = null
)
