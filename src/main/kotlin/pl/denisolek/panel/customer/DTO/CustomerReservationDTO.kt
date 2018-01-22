package pl.denisolek.panel.customer.DTO

import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.Reservation.ReservationState.*
import java.time.LocalDateTime

data class CustomerReservationDTO(
    var id: Int,
    var state: ReservationStateDTO,
    var startDateTime: LocalDateTime,
    var peopleNumber: Int,
    var floorName: String,
    var spotNumber: Int
) {
    companion object {
        fun fromReservation(reservation: Reservation): CustomerReservationDTO =
            CustomerReservationDTO(
                id = reservation.id!!,
                state = resolveState(reservation.state),
                startDateTime = reservation.startDateTime,
                peopleNumber = reservation.peopleNumber,
                floorName = reservation.spots[0].schemaItem?.floor?.name!!,
                spotNumber = reservation.spots[0].number
            )

        private fun resolveState(state: Reservation.ReservationState): ReservationStateDTO {
            return when (state) {
                PENDING, ACCEPTED -> ReservationStateDTO.UPCOMING
                CANCELED -> ReservationStateDTO.CANCELED
                FINISHED -> ReservationStateDTO.FINISHED
                NOT_APPEARED -> ReservationStateDTO.NOT_APPEARED
            }
        }
    }
}