package pl.denisolek.panel.customer.DTO

import java.time.LocalDateTime

data class CommentDTO(
        var id: Int? = null,
        var service_rate: Float,
        var food_rate: Float,
        var price_quality_rate: Float,
        var rate: Float,
        var dateTime: LocalDateTime
)