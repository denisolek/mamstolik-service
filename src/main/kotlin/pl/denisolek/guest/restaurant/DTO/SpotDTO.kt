package pl.denisolek.guest.restaurant.DTO

import java.time.LocalDate

data class SpotDTO(
        var id: Int,
        var number: Int,
        var floorId: Int,
        var floorName: String,
        var date: LocalDate,
        var reservations: List<SpotReservationDTO>
)