package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.customer.Customer

data class CommentCustomerDTO(
        var firstName: String,
        var lastName: String
) {
    companion object {
        fun fromCustomer(customer: Customer): CommentCustomerDTO =
                CommentCustomerDTO(
                        firstName = customer.firstName,
                        lastName = customer.lastName
                )
    }
}