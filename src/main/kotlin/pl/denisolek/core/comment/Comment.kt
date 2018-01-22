package pl.denisolek.core.comment

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.Restaurant
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var service_rate: Float,
    var text: String,
    var food_rate: Float,
    var price_quality_rate: Float,
    var rate: Float,
    var dateTime: LocalDateTime,
    @ManyToOne
    var reservation: Reservation,
    @ManyToOne
    var restaurant: Restaurant,
    @ManyToOne
    var customer: Customer
)