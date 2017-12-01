package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.restaurant.Restaurant
import java.time.LocalDate
import java.time.LocalTime

data class PanelReservationsDTO(
        var openTime: LocalTime? = null,
        var closeTime: LocalTime? = null,
        var reservations: List<PanelReservationDTO>
) {
    companion object {
        fun createPanelReservationDTO(restaurant: Restaurant, date: LocalDate) = PanelReservationsDTO(
                openTime = restaurant.getBusinessHoursForDate(date)?.openTime,
                closeTime = restaurant.getBusinessHoursForDate(date)?.closeTime,
                reservations = PanelReservationDTO.fromReservations(restaurant.reservations.filter { it.startDateTime.toLocalDate() == date })
        )
    }
}