package pl.denisolek.panel.customer

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO

@Service
class PanelCustomerService {
    fun getCustomers(restaurant: Restaurant): List<BaseCustomerInfoDTO> {
        return listOf()
    }
}