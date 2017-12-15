package pl.denisolek.core.reservation

import org.springframework.stereotype.Service
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant

@Service
class ReservationService(private val reservationRepository: ReservationRepository) {
    fun save(reservation: Reservation) =
            reservationRepository.save(reservation)

    fun getRestaurantCustomerReservations(restaurant: Restaurant, customer: Customer): List<Reservation> =
            reservationRepository.findByRestaurantAndCustomer(restaurant, customer)
}