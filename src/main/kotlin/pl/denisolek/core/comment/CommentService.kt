package pl.denisolek.core.comment

import org.springframework.stereotype.Service
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant

@Service
class CommentService(private val commentRepository: CommentRepository) {
    fun getRestaurantCustomerComments(restaurant: Restaurant, customer: Customer): List<Comment> =
        commentRepository.findByRestaurantAndCustomer(restaurant, customer)
}