package pl.denisolek.core.reservation

import pl.denisolek.core.BaseEntity
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Reservation(
        var date: LocalDate,
        var startDateTime: LocalDateTime,
        var endDateTime: LocalDateTime,
        var length: Duration,
        var peopleNumber: Int,
        var state: ReservationState,
        var verificationCode: Int,
        var isVerified: Boolean? = false,

        @ManyToOne
        var restaurant: Restaurant,

        @ManyToOne
        @JoinColumn
        var customer: Customer
) : BaseEntity() {
    enum class ReservationState {
        PENDING,
        ACCEPTED,
        CANCELED,
        DURING,
        FINISHED
    }
}
