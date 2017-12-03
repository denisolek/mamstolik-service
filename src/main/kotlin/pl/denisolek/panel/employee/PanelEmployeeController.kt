package pl.denisolek.panel.employee

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.User
import pl.denisolek.panel.employee.DTO.CreateEmployeeDTO
import pl.denisolek.panel.employee.DTO.EmployeeDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PanelEmployeeController(val panelEmployeeService: PanelEmployeeService) : PanelEmployeeApi {
    companion object {
        val API = PanelEmployeeApi.Companion
    }

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun getEmployees(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): List<EmployeeDTO> {
        return panelEmployeeService.getEmployees(restaurantId)
    }

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun addEmployee(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                             @RequestBody @Valid createEmployeeDTO: CreateEmployeeDTO): EmployeeDTO {
        return panelEmployeeService.addEmployee(createEmployeeDTO, restaurantId)
    }

    @ApiImplicitParams(
            ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer"),
            ApiImplicitParam(name = API.EMPLOYEE_ID, value = "Employee Id", paramType = "path", dataType = "integer")
    )
    override fun updateEmployee(@ApiIgnore @PathVariable(PanelEmployeeController.API.RESTAURANT_ID) restaurantId: Restaurant,
                                @ApiIgnore @PathVariable(PanelEmployeeController.API.EMPLOYEE_ID) employeeId: User,
                                @RequestBody @Valid createEmployeeDTO: CreateEmployeeDTO): EmployeeDTO {
        return panelEmployeeService.updateEmployee(createEmployeeDTO, restaurantId, employeeId)
    }
}