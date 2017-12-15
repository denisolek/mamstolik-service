package pl.denisolek.panel.customer.DTO

import pl.denisolek.core.customer.Customer

data class VipDTO(
        var vip: Boolean
) {
    companion object {
        fun fromCustomer(customer: Customer): VipDTO =
                VipDTO(
                        vip = customer.isVip
                )
    }
}