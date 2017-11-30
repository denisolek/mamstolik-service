package pl.denisolek.panel.reservation.DTO

import java.time.LocalTime

data class PanelReservationsDTO(
        var openTime: LocalTime? = null,
        var closeTime: LocalTime? = null,
        var reservations: List<PanelReservationDTO>
)