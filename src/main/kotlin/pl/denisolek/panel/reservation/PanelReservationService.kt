package pl.denisolek.panel.reservation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.customer.CustomerService
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.ReservationService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.reservation.DTO.ReservationCustomerDTO

@Service
class PanelReservationService(private val authorizationService: AuthorizationService,
                              private val reservationService: ReservationService,
                              private val customerService: CustomerService) {

    fun addReservation(restaurant: Restaurant, createReservationDTO: PanelCreateReservationDTO): List<PanelReservationDTO> {
        val reservationSpots = findReservationSpots(createReservationDTO, restaurant)
        if (!allSpotsAvailable(restaurant, createReservationDTO, reservationSpots))
            throw ServiceException(HttpStatus.BAD_REQUEST, "Some of provided spots at taken at ${createReservationDTO.dateTime}.")
        val reservation = reservationService.save(Reservation(
                panelCreateReservationDTO = createReservationDTO,
                restaurant = restaurant,
                approvedBy = authorizationService.getCurrentUser(),
                customer = prepareReservationCustomer(createReservationDTO, restaurant),
                spots = reservationSpots
        ))
        restaurant.reservations.add(reservation)
        return PanelReservationDTO.fromReservations(restaurant.reservations.filter { it.startDateTime.toLocalDate() == createReservationDTO.dateTime.toLocalDate() })
    }

    private fun allSpotsAvailable(restaurant: Restaurant, createReservationDTO: PanelCreateReservationDTO, reservationSpots: MutableList<Spot>) =
            restaurant.getAvailableSpotsAt(createReservationDTO.dateTime).containsAll(reservationSpots)

    private fun findReservationSpots(createReservationDTO: PanelCreateReservationDTO, restaurant: Restaurant): MutableList<Spot> {
        return createReservationDTO.spots.map { spotId ->
            restaurant.spots.find { it.id == spotId } ?: throw ServiceException(HttpStatus.NOT_FOUND, "Spot id $spotId doesn't exist or doesn't belong to the restaurant.")
        }.toMutableList()
    }

    private fun prepareReservationCustomer(createReservationDTO: PanelCreateReservationDTO, restaurant: Restaurant): Customer {
        return customerService.findOrCreate(ReservationCustomerDTO.createCustomer(
                reservationCustomerDTO = createReservationDTO.customer,
                user = authorizationService.getCurrentUser(),
                restaurant = restaurant
        ))
    }
}