package pl.denisolek.panel.customer

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import pl.denisolek.panel.customer.DTO.CustomerInfoDTO
import springfox.documentation.annotations.ApiIgnore

@RestController
class PanelCustomerController(val panelCustomerService: PanelCustomerService) : PanelCustomerApi {
    companion object {
        val API = PanelCustomerApi.Companion
    }

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun getCustomers(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): List<BaseCustomerInfoDTO> =
            panelCustomerService.getCustomers(restaurantId)

    @ApiImplicitParams(
            ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = API.CUSTOMER_ID, value = "Customer Id", paramType = "path", dataType = "int", required = true)
    )
    override fun getCustomer(@ApiIgnore @PathVariable(PanelCustomerApi.RESTAURANT_ID) restaurantId: Restaurant,
                             @ApiIgnore @PathVariable(PanelCustomerApi.CUSTOMER_ID) customerId: Customer): CustomerInfoDTO =
            panelCustomerService.getCustomer(restaurantId, customerId)
}