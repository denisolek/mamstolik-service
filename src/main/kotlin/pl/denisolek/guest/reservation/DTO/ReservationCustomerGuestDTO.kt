package pl.denisolek.guest.reservation.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.customer.Customer
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class ReservationCustomerGuestDTO(
        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "First name is not valid")
        var firstName: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "Last name is not valid")
        var lastName: String,

        @field:Size(max = 100, message = "Email too long.")
        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"

        fun createCustomer(reservationCustomerDTO: ReservationCustomerGuestDTO): Customer =
                Customer(
                        email = reservationCustomerDTO.email,
                        firstName = reservationCustomerDTO.firstName,
                        lastName = reservationCustomerDTO.lastName,
                        phoneNumber = reservationCustomerDTO.phoneNumber,
                        isVip = false
                )

        fun fromCustomer(customer: Customer) = ReservationCustomerGuestDTO(
                firstName = customer.firstName,
                lastName = customer.lastName,
                email = customer.email,
                phoneNumber = customer.phoneNumber
        )
    }
}