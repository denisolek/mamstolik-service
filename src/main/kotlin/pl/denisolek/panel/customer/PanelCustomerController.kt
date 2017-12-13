package pl.denisolek.panel.customer

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import springfox.documentation.annotations.ApiIgnore

@RestController
class PanelCustomerController(val panelCustomerService: PanelCustomerService): PanelCustomerApi {

    companion object {
        val API = PanelCustomerApi.Companion
    }

    override fun getCustomers(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): List<BaseCustomerInfoDTO> =
            panelCustomerService.getCustomers(restaurantId)
}