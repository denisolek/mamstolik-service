package pl.denisolek.core.reservation

import org.springframework.data.jpa.repository.JpaRepository
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant

interface ReservationRepository : JpaRepository<Reservation, Int> {
    fun findByRestaurantAndCustomer(restaurantId: Restaurant, customerId: Customer): List<Reservation>
}