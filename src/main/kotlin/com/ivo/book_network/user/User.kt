package com.ivo.book_network.user

import com.ivo.book_network.role.Role
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.security.Principal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue
    private val id: Int?,
    private val firstname: String,
    private val lastname: String,
    private val dateOfBirth: LocalDate,
    @Column(unique = true)
    private val email: String,
    private val password: String,
    private val accountLocked: Boolean,
    private val enabled: Boolean,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private val createdDate: LocalDateTime?,
    @LastModifiedDate
    @Column(insertable = false)
    private val lastModifiedDate: LocalDateTime?,
    @ManyToMany(fetch = FetchType.EAGER)
    private val roles: List<Role>
) : UserDetails, Principal {
    override fun getName(): String {
        return email
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { role ->
            SimpleGrantedAuthority(role.name)
        }.toList()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !accountLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    private fun fullName(): String {
        return "$firstname $lastname"
    }
}