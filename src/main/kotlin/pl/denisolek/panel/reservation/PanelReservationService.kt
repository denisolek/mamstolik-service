package pl.denisolek.panel.reservation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.CustomerService
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.ReservationService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.reservation.DTO.PanelCustomerInfoDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO

@Service
class PanelReservationService(private val authorizationService: AuthorizationService,
                              private val reservationService: ReservationService,
                              private val customerService: CustomerService) {
    fun addReservation(restaurant: Restaurant, reservationDTO: PanelReservationDTO): List<Reservation> {
        val customer = customerService.findOrCreate(PanelCustomerInfoDTO.createCustomer(
                panelCustomerInfoDTO = reservationDTO.customerInfo,
                user = authorizationService.getCurrentUser(),
                restaurant = restaurant
        ))

        val reservation = Reservation(
                panelReservationDTO = reservationDTO,
                restaurant = restaurant,
                approvedBy = authorizationService.getCurrentUser(),
                customer = customer,
                spots = reservationDTO.spots.map { spotId ->
                    restaurant.spots.find { it.id == spotId } ?: throw ServiceException(HttpStatus.NOT_FOUND, "Spot id $spotId doesn't exist or doesn't belong to the restaurant.")
                }.toMutableList()
        )

        return listOf()
    }
}