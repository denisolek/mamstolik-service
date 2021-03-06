package pl.denisolek.panel.customer

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.customer.Customer
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import pl.denisolek.panel.customer.DTO.CustomerInfoDTO
import pl.denisolek.panel.customer.DTO.VipDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Customer controller", tags = arrayOf("Customer"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelCustomerApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val CUSTOMER_ID: String = "customerId"

        const val CUSTOMERS_PATH = "/{$RESTAURANT_ID}/customers"
        const val CUSTOMERS_ID_PATH = "$CUSTOMERS_PATH/{$CUSTOMER_ID}"
        const val CUSTOMERS_ID_CHANGE_VIP_PATH = "$CUSTOMERS_ID_PATH/change-vip"
    }

    @GetMapping(CUSTOMERS_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun getCustomers(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): List<BaseCustomerInfoDTO>

    @GetMapping(CUSTOMERS_ID_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun getCustomer(
        @ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
        @ApiIgnore @PathVariable(CUSTOMER_ID) customerId: Customer
    ): CustomerInfoDTO

    @PutMapping(CUSTOMERS_ID_CHANGE_VIP_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun changeVipStatus(
        @ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
        @ApiIgnore @PathVariable(CUSTOMER_ID) customerId: Customer,
        @RequestBody vipDTO: VipDTO
    ): VipDTO
}