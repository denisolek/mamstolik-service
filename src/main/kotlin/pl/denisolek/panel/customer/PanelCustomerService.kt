package pl.denisolek.panel.customer

import org.springframework.stereotype.Service
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import pl.denisolek.panel.customer.DTO.CustomerInfoDTO

@Service
class PanelCustomerService {
    fun getCustomers(restaurant: Restaurant): List<BaseCustomerInfoDTO> =
            restaurant.reservations
                    .map { BaseCustomerInfoDTO.fromCustomer(it.customer) }
                    .distinct()
                    .sortedBy { it.lastName }

    fun getCustomer(restaurant: Restaurant, customer: Customer): CustomerInfoDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}