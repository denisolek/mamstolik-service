package pl.denisolek.core.reservation

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.user.User
import pl.denisolek.infrastructure.util.DateTimeInterval
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import java.time.Duration
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Reservation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        override var startDateTime: LocalDateTime,
        override var endDateTime: LocalDateTime,
        var peopleNumber: Int,
        var state: ReservationState,
        var verificationCode: Int? = null,
        var duration: Duration,
        var isVerified: Boolean? = false,
        var note: String? = "",

        @ManyToOne
        var restaurant: Restaurant,

        @ManyToOne
        @JoinColumn
        var customer: Customer,

        @ManyToOne
        @JoinColumn
        var approvedBy: User? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "reservation_spots", joinColumns = arrayOf(JoinColumn(name = "reservation_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "spot_id")))
        var spots: MutableList<Spot> = mutableListOf()
) : DateTimeInterval {
    constructor(panelReservationDTO: PanelReservationDTO, restaurant: Restaurant, customer: Customer, approvedBy: User, spots: MutableList<Spot>) : this(
            startDateTime = panelReservationDTO.date,
            endDateTime = panelReservationDTO.date.plus(restaurant.avgReservationTime),
            peopleNumber = panelReservationDTO.peopleNumber,
            state = ReservationState.ACCEPTED,
            verificationCode = null,
            duration = restaurant.avgReservationTime,
            isVerified = true,
            note = panelReservationDTO.note,
            restaurant = restaurant,
            customer = customer,
            approvedBy = approvedBy,
            spots = spots
    )

    enum class ReservationState {
        PENDING,
        ACCEPTED,
        CANCELED,
        DURING,
        FINISHED
    }
}
