package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.reservation.Reservation
import java.time.LocalTime

data class PanelReservationDTO(
        var customer: ReservationCustomerDTO,
        var peopleNumber: Int,
        var time: LocalTime,
        var spots: List<ReservationSpotInfoDTO>,
        var note: String? = null,
        var state: Reservation.ReservationState
) {
    companion object {
        fun fromReservations(reservations: List<Reservation>) =
                reservations.map {
                    PanelReservationDTO(
                            customer = ReservationCustomerDTO.fromCustomer(it.customer),
                            peopleNumber = it.peopleNumber,
                            time = it.startDateTime.toLocalTime(),
                            spots = ReservationSpotInfoDTO.fromSpots(it.spots),
                            note = it.note,
                            state = it.state
                    )
                }
    }
}