package pl.denisolek.core.reservation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.infrastructure.util.isBeforeOrEqual
import java.time.LocalDateTime

@Service
class ReservationService(private val reservationRepository: ReservationRepository) {
    fun save(reservation: Reservation) =
        reservationRepository.save(reservation)

    fun getRestaurantCustomerReservations(restaurant: Restaurant, customer: Customer): List<Reservation> =
        reservationRepository.findByRestaurantAndCustomer(restaurant, customer)

    fun validateReservationTime(dateTime: LocalDateTime) {
        if (dateTime.isBeforeOrEqual(LocalDateTime.now()))
            throw ServiceException(HttpStatus.BAD_REQUEST, "You can't make reservations in the past.")
        if (dateTime.toLocalTime().minute % 15 != 0)
            throw ServiceException(HttpStatus.BAD_REQUEST, "You can make only at 0, 15, 30, 45.")
    }

    fun findReservationSpots(spots: List<Int>, restaurant: Restaurant): MutableList<Spot> {
        return spots.map { spotId ->
            restaurant.spots.find { it.id == spotId } ?: throw ServiceException(
                HttpStatus.NOT_FOUND,
                "Spot id $spotId doesn't exist or doesn't belong to the restaurant."
            )
        }.toMutableList()
    }

    fun allSpotsAvailable(restaurant: Restaurant, dateTime: LocalDateTime, reservationSpots: MutableList<Spot>) =
        restaurant.getAvailableSpotsAt(dateTime).containsAll(reservationSpots)
}