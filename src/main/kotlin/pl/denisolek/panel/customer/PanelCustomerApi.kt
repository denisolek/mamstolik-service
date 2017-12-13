package pl.denisolek.panel.customer

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.customer.DTO.BaseCustomerInfoDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Customer controller", tags = arrayOf("Customer"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelCustomerApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val CUSTOMERS_PATH = "/{$RESTAURANT_ID}/customers"
    }

    @GetMapping(CUSTOMERS_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun getCustomers(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): List<BaseCustomerInfoDTO>
}