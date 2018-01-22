package pl.denisolek.panel.customer.DTO

import pl.denisolek.core.customer.Customer

data class BaseCustomerInfoDTO(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var isVip: Boolean
) {
    companion object {
        fun fromCustomer(customer: Customer): BaseCustomerInfoDTO =
            BaseCustomerInfoDTO(
                id = customer.id!!,
                firstName = customer.firstName,
                lastName = customer.lastName,
                phoneNumber = customer.phoneNumber,
                isVip = customer.isVip
            )
    }
}