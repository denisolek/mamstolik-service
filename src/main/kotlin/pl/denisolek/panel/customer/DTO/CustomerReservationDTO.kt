package pl.denisolek.panel.customer.DTO

import java.time.LocalDateTime

data class CustomerReservationDTO(
        var id: Int,
        var state: ReservationStateDTO,
        var startDateTime: LocalDateTime,
        var peopleNumber: Int,
        var floorName: String,
        var spotNumber: Int
)