package pl.denisolek.panel.reservation.DTO

import java.time.LocalDateTime

data class PanelCreateReservationDTO(
        var customer: ReservationCustomerDTO,
        var peopleNumber: Int,
        var dateTime: LocalDateTime,
        var spots: List<Int>,
        var note: String = ""
)