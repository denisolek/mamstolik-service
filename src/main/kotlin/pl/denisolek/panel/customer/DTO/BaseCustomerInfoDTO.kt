package pl.denisolek.panel.customer.DTO

data class BaseCustomerInfoDTO(
        var id: Int,
        var firstName: String,
        var lastName: String,
        var phoneNumber: String,
        var isVip: Boolean
)