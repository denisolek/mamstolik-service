package pl.denisolek.core.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.denisolek.core.restaurant.Restaurant

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User
    fun countByUsername(username: String): Int
    fun findByUsername(username: String): User
    fun findByRestaurant(restaurant: Restaurant): User?

    @Query("select distinct u from User as u left join u.restaurant as r " +
            "where (lower(r.urlName) like lower(:name))")
    fun findByUrlName(@Param(value = "name") name: String): User?
}
