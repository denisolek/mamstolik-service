package pl.denisolek.panel.employee

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.User
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.employee.DTO.CreateEmployeeDTO
import pl.denisolek.panel.employee.DTO.EmployeeDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api("Employee controller", tags = arrayOf("Employee"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelEmployeeApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val EMPLOYEE_ID: String = "employeeId"

        const val EMPLOYEES_PATH = "/{$RESTAURANT_ID}/employees"
        const val EMPLOYEES_ID_PATH = "$EMPLOYEES_PATH/{$EMPLOYEE_ID}"
    }

    @GetMapping(EMPLOYEES_PATH)
//    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
//            "@authorizationService.currentUser.workPlace == #restaurantId")
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId)")
    fun getEmployees(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): List<EmployeeDTO>

    @PostMapping(EMPLOYEES_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId)")
    fun addEmployee(@ApiIgnore @PathVariable(PanelEmployeeController.API.RESTAURANT_ID) restaurantId: Restaurant,
                    @RequestBody @Valid createEmployeeDTO: CreateEmployeeDTO): List<EmployeeDTO>

    @PutMapping(EMPLOYEES_ID_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId)")
    fun updateEmployee(@ApiIgnore @PathVariable(PanelEmployeeController.API.RESTAURANT_ID) restaurantId: Restaurant,
                       @ApiIgnore @PathVariable(PanelEmployeeController.API.EMPLOYEE_ID) employeeId: User,
                       @RequestBody @Valid createEmployeeDTO: CreateEmployeeDTO): List<EmployeeDTO>
}