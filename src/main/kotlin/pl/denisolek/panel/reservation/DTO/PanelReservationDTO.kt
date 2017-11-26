package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.user.User
import java.time.LocalDateTime

data class PanelReservationDTO(
        var customerInfo: PanelCustomerInfoDTO,
        var peopleNumber: Int,
        var date: LocalDateTime,
        var spotId: Int,
        var note: String = "",
        var createdBy: User
)