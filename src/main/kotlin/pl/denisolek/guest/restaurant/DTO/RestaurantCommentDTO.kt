package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.comment.Comment
import java.time.LocalDateTime

data class RestaurantCommentDTO(
        var id: Int? = null,
        var text: String,
        var service_rate: Float,
        var food_rate: Float,
        var price_quality_rate: Float,
        var rate: Float,
        var dateTime: LocalDateTime,
        var customer: CommentCustomerDTO
) {
    companion object {
        fun fromComment(comment: Comment): RestaurantCommentDTO =
                RestaurantCommentDTO(
                        id = comment.id,
                        text = comment.text,
                        service_rate = comment.service_rate,
                        food_rate = comment.food_rate,
                        price_quality_rate = comment.price_quality_rate,
                        rate = comment.rate,
                        dateTime = comment.dateTime,
                        customer = CommentCustomerDTO.fromCustomer(comment.customer)
                )
    }
}