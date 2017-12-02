package pl.denisolek.panel.reservation.DTO

import org.hibernate.validator.constraints.Email
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.User

data class ReservationCustomerDTO(
        var firstName: String? = null,
        var lastName: String? = null,
        @field:Email
        var email: String? = null,
        var phoneNumber: String? = null
) {
    companion object {
        fun createCustomer(reservationCustomerDTO: ReservationCustomerDTO, user: User, restaurant: Restaurant): Customer =
                Customer(
                        email = reservationCustomerDTO.email ?: user.email,
                        firstName = reservationCustomerDTO.firstName ?: user.firstName ?: restaurant.name,
                        lastName = reservationCustomerDTO.lastName ?: user.lastName ?: "",
                        phoneNumber = reservationCustomerDTO.phoneNumber ?: restaurant.phoneNumber
                )

        fun fromCustomer(customer: Customer) = ReservationCustomerDTO(
                firstName = customer.firstName,
                lastName = customer.lastName,
                email = customer.email,
                phoneNumber = customer.phoneNumber
        )
    }
}