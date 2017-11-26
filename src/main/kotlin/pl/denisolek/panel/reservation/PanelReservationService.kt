package pl.denisolek.panel.reservation

import org.springframework.stereotype.Service
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO

@Service
class PanelReservationService {
    fun addReservation(restaurant: Restaurant, reservationDTO: PanelReservationDTO): Reservation {

    }
}