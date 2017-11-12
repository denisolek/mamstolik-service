package pl.denisolek.core.reservation

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.util.DateTimeInterval
import java.time.Duration
import java.time.LocalDateTime
import javax.persistence.*

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

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "reservation_spots", joinColumns = arrayOf(JoinColumn(name = "reservation_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "spot_id")))
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
