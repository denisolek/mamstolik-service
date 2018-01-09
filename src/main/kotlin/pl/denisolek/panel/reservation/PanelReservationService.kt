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
        reservationService.validateReservationTime(createReservationDTO.dateTime)
        val reservationSpots = reservationService.findReservationSpots(createReservationDTO.spots, restaurant)
        if (!reservationService.allSpotsAvailable(restaurant, createReservationDTO.dateTime, reservationSpots))
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

    private fun prepareReservationCustomer(createReservationDTO: PanelCreateReservationDTO, restaurant: Restaurant): Customer {
        if (createReservationDTO.customer.phoneNumber.isNullOrBlank()) {
            createReservationDTO.customer.phoneNumber = restaurant.phoneNumber
            createReservationDTO.customer.firstName = restaurant.name
            createReservationDTO.customer.email = restaurant.email
        }

        return customerService.findOrCreate(ReservationCustomerDTO.createCustomer(
                reservationCustomerDTO = createReservationDTO.customer,
                user = authorizationService.getCurrentUser(),
                restaurant = restaurant
        ))
    }

    fun getReservations(restaurant: Restaurant, date: LocalDate): PanelReservationsDTO =
            PanelReservationsDTO.createPanelReservationDTO(restaurant, date)

    fun editReservation(restaurant: Restaurant, reservation: Reservation, createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO {
        reservationService.validateReservationTime(createReservationDTO.dateTime)
        validateReservationAssignment(reservation, restaurant)
        val reservationSpots = reservationService.findReservationSpots(createReservationDTO.spots, restaurant)
        if (!reservationService.allSpotsAvailable(restaurant, createReservationDTO.dateTime, reservationSpots))
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

    fun getSpotReservations(restaurant: Restaurant, spot: Spot, date: LocalDate): SpotReservationsDTO {
        if (spot.restaurant != restaurant) throw ServiceException(HttpStatus.FORBIDDEN, "Access denied.")
        return SpotReservationsDTO.fromSpotDateReservations(
                spot = spot,
                date = date,
                reservations = restaurant.reservations.filter { it.startDateTime.toLocalDate() == date && it.spots.contains(spot) }
        )
    }
}