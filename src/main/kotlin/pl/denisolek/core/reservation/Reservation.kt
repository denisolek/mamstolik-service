package pl.denisolek.core.reservation

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.DateTimeInterval
import java.time.Duration
import java.time.LocalDateTime
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Reservation(
        override var startDateTime: LocalDateTime,
        override var endDateTime: LocalDateTime,
        var peopleNumber: Int,
        var state: ReservationState,
        var verificationCode: Int,
        var duration: Duration,
        var isVerified: Boolean? = false,

        @ManyToOne
        var restaurant: Restaurant,

        @ManyToOne
        @JoinColumn
        var customer: Customer,

        @ElementCollection
        var spots: List<Spot>
) : BaseEntity(), DateTimeInterval {
    enum class ReservationState {
        PENDING,
        ACCEPTED,
        CANCELED,
        DURING,
        FINISHED
    }
}
