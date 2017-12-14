package pl.denisolek.panel.customer.DTO

import pl.denisolek.core.comment.Comment
import java.time.LocalDateTime

data class CommentDTO(
        var id: Int? = null,
        var service_rate: Float,
        var food_rate: Float,
        var price_quality_rate: Float,
        var rate: Float,
        var dateTime: LocalDateTime
) {
    companion object {
        fun fromComment(comment: Comment): CommentDTO =
                CommentDTO(
                        id = comment.id,
                        service_rate = comment.service_rate,
                        food_rate = comment.food_rate,
                        price_quality_rate = comment.price_quality_rate,
                        rate = comment.rate,
                        dateTime = comment.dateTime
                )
    }
}