package pl.denisolek.core.comment

import org.springframework.data.jpa.repository.JpaRepository
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant

interface CommentRepository : JpaRepository<Comment, Int> {
    fun findByRestaurantAndCustomer(restaurantId: Restaurant, customerId: Customer): List<Comment>
}