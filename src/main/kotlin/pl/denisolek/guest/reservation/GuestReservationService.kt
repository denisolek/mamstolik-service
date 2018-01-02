package pl.denisolek.guest.reservation

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.customer.CustomerService
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.ReservationService
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO
import pl.denisolek.guest.reservation.DTO.ReservationCustomerGuestDTO

@Service
class GuestReservationService(private val reservationService: ReservationService,
                              private val restaurantService: RestaurantService,
                              private val customerService: CustomerService) {

    fun addReservation(dto: CreateReservationGuestDTO) {
        val restaurant = restaurantService.findById(dto.restaurantId) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Restaurant not found.")
        val reservationSpots = reservationService.findReservationSpots(dto.spots, restaurant)
        if (!reservationService.allSpotsAvailable(restaurant, dto.dateTime, reservationSpots))
            throw ServiceException(HttpStatus.BAD_REQUEST, "Some of provided spots at taken at ${dto.dateTime}.")
        val verificationCode = RandomStringUtils.randomNumeric(6)
        val reservation = reservationService.save(Reservation(
                panelCreateReservationGuestDTO = dto,
                restaurant = restaurant,
                customer = prepareReservationCustomer(dto),
                spots = reservationSpots,
                verificationCode = verificationCode
        ))
        restaurant.reservations.add(reservation)
    }

    private fun prepareReservationCustomer(dto: CreateReservationGuestDTO): Customer {
        return customerService.findOrCreate(ReservationCustomerGuestDTO.createCustomer(
                reservationCustomerDTO = dto.customer
        ))
    }
}