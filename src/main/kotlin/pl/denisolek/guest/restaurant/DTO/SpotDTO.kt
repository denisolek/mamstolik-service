package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.spot.Spot
import java.time.LocalDate

data class SpotDTO(
        var id: Int,
        var number: Int,
        var floorId: Int,
        var floorName: String,
        var date: LocalDate,
        var reservations: List<SpotReservationDTO>
) {
    companion object {
        fun fromSpotDateReservations(spot: Spot, date: LocalDate, reservations: List<Reservation>?) =
                SpotDTO(
                        id = spot.id!!,
                        number = spot.number,
                        floorId = spot.schemaItem?.floor?.id!!,
                        floorName = spot.schemaItem?.floor?.name ?: "",
                        date = date,
                        reservations = reservations?.map { SpotReservationDTO.fromReservation(it) } ?: listOf()
                )
    }
}