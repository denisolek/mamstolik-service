package pl.denisolek.panel.reservation.DTO

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.User

data class PanelCustomerInfoDTO(
        var firstName: String? = null,
        var lastName: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null
) {
    companion object {
        fun createCustomer(panelCustomerInfoDTO: PanelCustomerInfoDTO, user: User, restaurant: Restaurant): Customer =
                Customer(
                        email = panelCustomerInfoDTO.email ?: user.email,
                        firstName = panelCustomerInfoDTO.firstName ?: user.firstName ?: restaurant.name,
                        lastName = panelCustomerInfoDTO.lastName ?: user.lastName ?: "",
                        phoneNumber = panelCustomerInfoDTO.phoneNumber ?: restaurant.phoneNumber
                )
    }
}