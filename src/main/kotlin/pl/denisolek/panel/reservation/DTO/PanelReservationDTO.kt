package pl.denisolek.panel.reservation.DTO

import java.time.LocalDateTime

data class PanelReservationDTO(
        var customerInfo: PanelCustomerInfoDTO,
        var peopleNumber: Int,
        var date: LocalDateTime,
        var spots: List<Int>,
        var note: String = ""
)