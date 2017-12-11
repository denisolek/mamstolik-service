package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.reservation.Reservation
import java.time.LocalTime

data class PanelReservationDTO(
        var id: Int,
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
                            id = it.id!!,
                            customer = ReservationCustomerDTO.fromCustomer(it.customer),
                            peopleNumber = it.peopleNumber,
                            time = it.startDateTime.toLocalTime(),
                            spots = ReservationSpotInfoDTO.fromSpots(it.spots),
                            note = it.note,
                            state = it.state
                    )
                }

        fun fromReservation(reservation: Reservation) =
                PanelReservationDTO(
                        id = reservation.id!!,
                        customer = ReservationCustomerDTO.fromCustomer(reservation.customer),
                        peopleNumber = reservation.peopleNumber,
                        time = reservation.startDateTime.toLocalTime(),
                        spots = ReservationSpotInfoDTO.fromSpots(reservation.spots),
                        note = reservation.note,
                        state = reservation.state
                )
    }
}