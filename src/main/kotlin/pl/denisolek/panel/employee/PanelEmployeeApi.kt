package pl.denisolek.panel.employee

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.employee.DTO.EmployeeDTO
import springfox.documentation.annotations.ApiIgnore

@Api("Employee controller", tags = arrayOf("Employee"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelEmployeeApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val EMPLOYEES_PATH = "/{$RESTAURANT_ID}/employees"
    }

    @GetMapping(EMPLOYEES_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId)")
    fun getEmployees(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): List<EmployeeDTO>
}