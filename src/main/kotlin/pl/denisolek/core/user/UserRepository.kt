package pl.denisolek.core.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User
    fun countByUsername(username: String): Int
    fun findByUsername(username: String): User
}
