package pl.denisolek.panel.customer

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.comment.CommentService
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.reservation.ReservationService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import pl.denisolek.panel.customer.DTO.CustomerInfoDTO

@Service
class PanelCustomerService(private val commentService: CommentService,
                           private val reservationService: ReservationService) {
    fun getCustomers(restaurant: Restaurant): List<BaseCustomerInfoDTO> =
            restaurant.reservations
                    .map { BaseCustomerInfoDTO.fromCustomer(it.customer) }
                    .distinct()
                    .sortedBy { it.lastName }

    fun getCustomer(restaurant: Restaurant, customer: Customer): CustomerInfoDTO {
        val customerComments = commentService.getRestaurantCustomerComments(restaurant, customer)
        val customerReservations = reservationService.getRestaurantCustomerReservations(restaurant, customer)
        if (customerReservations.isEmpty()) throw ServiceException(HttpStatus.FORBIDDEN, "Access denied.")
        return CustomerInfoDTO.getCustomerInfoDTO(restaurant, customer, customerComments, customerReservations)
    }
}