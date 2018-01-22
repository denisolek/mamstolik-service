package pl.denisolek.panel.reservation.DTO

import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.reservation.Reservation.ReservationState

data class ReservationStateDTO(
    @NotBlank
    var state: ReservationState
)