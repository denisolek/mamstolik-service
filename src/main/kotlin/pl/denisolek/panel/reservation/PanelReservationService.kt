package pl.denisolek.panel.reservation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.customer.CustomerService
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.Reservation.ReservationState.CANCELED
import pl.denisolek.core.reservation.ReservationService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.config.security.AuthorizationService
import pl.denisolek.panel.reservation.DTO.*
import java.time.LocalDate

@Service
class PanelReservationService(private val authorizationService: AuthorizationService,
                              private val reservationService: ReservationService,
                              private val restaurantService: RestaurantService,
                              private val customerService: CustomerService) {

    fun addReservation(restaurant: Restaurant, createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO {
        validateReservationTime(createReservationDTO)
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
        return PanelReservationsDTO.createPanelReservationDTO(restaurant, createReservationDTO.dateTime.toLocalDate())
    }

    private fun validateReservationTime(createReservationDTO: PanelCreateReservationDTO) {
        if (createReservationDTO.dateTime.toLocalTime().minute % 15 != 0)
            throw ServiceException(HttpStatus.BAD_REQUEST, "You can make only at 0, 15, 30, 45.")
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

    fun getReservations(restaurant: Restaurant, date: LocalDate): PanelReservationsDTO =
            PanelReservationsDTO.createPanelReservationDTO(restaurant, date)

    fun editReservation(restaurant: Restaurant, reservation: Reservation, createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO {
        validateReservationTime(createReservationDTO)
        validateReservationAssignment(reservation, restaurant)
        val reservationSpots = findReservationSpots(createReservationDTO, restaurant)
        if (!allSpotsAvailable(restaurant, createReservationDTO, reservationSpots))
            throw ServiceException(HttpStatus.BAD_REQUEST, "Some of provided spots at taken at ${createReservationDTO.dateTime}.")
        val reservation = reservationService.save(Reservation(
                id = reservation.id,
                panelCreateReservationDTO = createReservationDTO,
                restaurant = restaurant,
                approvedBy = authorizationService.getCurrentUser(),
                customer = prepareReservationCustomer(createReservationDTO, restaurant),
                spots = reservationSpots
        ))
        restaurant.reservations.removeIf { it.id == reservation.id }
        restaurant.reservations.add(reservation)
        return PanelReservationsDTO.createPanelReservationDTO(restaurant, createReservationDTO.dateTime.toLocalDate())
    }

    private fun validateReservationAssignment(reservation: Reservation, restaurant: Restaurant) {
        if (reservation.restaurant != restaurant)
            throw ServiceException(HttpStatus.BAD_REQUEST, "This reservation is assigned to another restaurant.")
    }

    fun cancelReservation(restaurant: Restaurant, reservation: Reservation): PanelReservationsDTO {
        validateReservationAssignment(reservation, restaurant)
        restaurant.reservations.find { it == reservation }.let { it?.state = CANCELED }
        val updatedRestaurant = restaurantService.save(restaurant)
        return PanelReservationsDTO.createPanelReservationDTO(updatedRestaurant, reservation.startDateTime.toLocalDate())
    }

    fun getReservation(restaurant: Restaurant, reservation: Reservation): PanelReservationDTO =
            PanelReservationDTO.fromReservation(reservation)

    fun changeReservationState(restaurant: Restaurant, reservation: Reservation, stateDTO: ReservationStateDTO): PanelReservationDTO {
        reservation.state = stateDTO.state
        return PanelReservationDTO.fromReservation(reservationService.save(reservation))
    }
}