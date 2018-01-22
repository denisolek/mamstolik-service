package pl.denisolek.guest.reservation.DTO

import java.time.LocalDateTime
import javax.validation.Valid

data class CreateReservationGuestDTO(
    var restaurantId: Int,
    @field:Valid
    var customer: ReservationCustomerGuestDTO,
    var peopleNumber: Int,
    var dateTime: LocalDateTime,
    var spots: List<Int>,
    var note: String = ""
)