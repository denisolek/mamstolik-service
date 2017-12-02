package pl.denisolek.core.user

import org.springframework.data.jpa.repository.JpaRepository
import pl.denisolek.core.restaurant.Restaurant

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User
    fun countByUsername(username: String): Int
    fun findByUsername(username: String): User
    fun findByRestaurant(restaurant: Restaurant): User?
}
