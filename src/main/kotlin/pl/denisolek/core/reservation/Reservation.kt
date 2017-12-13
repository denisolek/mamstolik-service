package pl.denisolek.core.reservation

import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.user.User
import pl.denisolek.infrastructure.util.DateTimeInterval
import pl.denisolek.infrastructure.util.isAfterOrEqual
import pl.denisolek.infrastructure.util.isBeforeOrEqual
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
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
        var verificationCode: Int? = null,
        var duration: Duration,
        var isVerified: Boolean? = false,
        var note: String? = "",

        @Enumerated(EnumType.STRING)
        var state: ReservationState,
        
        @ManyToOne
        var restaurant: Restaurant,

        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        @JoinColumn
        var customer: Customer,

        @ManyToOne
        @JoinColumn
        var approvedBy: User? = null,

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "reservation_spots", joinColumns = arrayOf(JoinColumn(name = "reservation_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "spot_id")))
        var spots: MutableList<Spot> = mutableListOf()
) : DateTimeInterval {
    constructor(id: Int? = null, panelCreateReservationDTO: PanelCreateReservationDTO, restaurant: Restaurant, customer: Customer, approvedBy: User, spots: MutableList<Spot>) : this(
            id = id,
            startDateTime = panelCreateReservationDTO.dateTime.withSecond(0).withNano(0),
            endDateTime = panelCreateReservationDTO.dateTime.plus(restaurant.avgReservationTime).withSecond(0).withNano(0),
            peopleNumber = panelCreateReservationDTO.peopleNumber,
            state = ReservationState.ACCEPTED,
            verificationCode = null,
            duration = restaurant.avgReservationTime,
            isVerified = true,
            note = panelCreateReservationDTO.note,
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

    fun validate() {
        val haveSpecialDates = this.restaurant.specialDates.any { it.date == this.startDateTime.toLocalDate() }

        val validSpecialDates = this.restaurant.specialDates.find { it.date == this.startDateTime.toLocalDate() }?.let {
            this.isInsideBusinessHours(it.businessHour)
        } ?: true

        val validBusinessHours = this.restaurant.getBusinessHoursForDate(this.startDateTime.toLocalDate())?.let {
            this.isInsideBusinessHours(it)
        } ?: false

        when {
            haveSpecialDates && !validSpecialDates ->
                throw ServiceException(HttpStatus.BAD_REQUEST, "Reservation (id ${this.id}, date ${this.startDateTime.toLocalDate()}, day ${this.startDateTime.toLocalDate().dayOfWeek}) doesn't fit any special dates.")
            !haveSpecialDates && !validBusinessHours ->
                throw ServiceException(HttpStatus.BAD_REQUEST, "Reservation (id ${this.id}, date ${this.startDateTime.toLocalDate()}, day ${this.startDateTime.toLocalDate().dayOfWeek}) doesn't fit any business hour.")
        }
    }

    private fun isInsideBusinessHours(businessHour: BusinessHour) = !businessHour.isClosed &&
            this.startDateTime.toLocalTime().isAfterOrEqual(businessHour.openTime) &&
            this.endDateTime.toLocalTime().isBeforeOrEqual(businessHour.closeTime)
}
