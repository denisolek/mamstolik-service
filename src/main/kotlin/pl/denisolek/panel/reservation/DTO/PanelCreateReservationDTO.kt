package pl.denisolek.panel.reservation.DTO

import java.time.LocalDateTime
import javax.validation.Valid

data class PanelCreateReservationDTO(
    @field:Valid
    var customer: ReservationCustomerDTO,
    var peopleNumber: Int,
    var dateTime: LocalDateTime,
    var spots: List<Int>,
    var note: String = ""
)