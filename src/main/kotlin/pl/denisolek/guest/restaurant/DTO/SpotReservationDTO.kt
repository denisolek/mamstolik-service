package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.reservation.Reservation
import pl.denisolek.panel.reservation.DTO.ReservationCustomerDTO
import java.time.LocalTime

data class SpotReservationDTO(
    var id: Int,
    var customer: ReservationCustomerDTO,
    var peopleNumber: Int,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var note: String? = null,
    var state: Reservation.ReservationState
) {
    companion object {
        fun fromReservation(reservation: Reservation) = SpotReservationDTO(
            id = reservation.id!!,
            customer = ReservationCustomerDTO.fromCustomer(reservation.customer),
            peopleNumber = reservation.peopleNumber,
            startTime = reservation.startDateTime.toLocalTime(),
            endTime = reservation.endDateTime.toLocalTime(),
            note = reservation.note,
            state = reservation.state
        )
    }
}