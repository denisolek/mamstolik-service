package pl.denisolek.core.reservation

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.util.isBeforeOrEqual
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import java.time.LocalDateTime

@Service
class ReservationService(private val reservationRepository: ReservationRepository) {
    fun save(reservation: Reservation) =
            reservationRepository.save(reservation)

    fun getRestaurantCustomerReservations(restaurant: Restaurant, customer: Customer): List<Reservation> =
            reservationRepository.findByRestaurantAndCustomer(restaurant, customer)

    fun validateReservationTime(createReservationDTO: PanelCreateReservationDTO) {
        if (createReservationDTO.dateTime.isBeforeOrEqual(LocalDateTime.now()))
            throw ServiceException(HttpStatus.BAD_REQUEST, "You can't make reservations in the past.")
        if (createReservationDTO.dateTime.toLocalTime().minute % 15 != 0)
            throw ServiceException(HttpStatus.BAD_REQUEST, "You can make only at 0, 15, 30, 45.")
    }
}