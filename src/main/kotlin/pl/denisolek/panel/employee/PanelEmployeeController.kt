package pl.denisolek.panel.employee

import io.swagger.annotations.ApiImplicitParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
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
                             @RequestBody @Valid employeeDTO: EmployeeDTO): List<EmployeeDTO> {
        return panelEmployeeService.addEmployee(employeeDTO, restaurantId)
    }
}