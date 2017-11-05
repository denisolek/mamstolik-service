package pl.denisolek.panel.employee

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.employee.DTO.EmployeeDTO

@Service
class PanelEmployeeService {
    fun getEmployees(restaurant: Restaurant): List<EmployeeDTO> {
        return restaurant.employees?.map { EmployeeDTO.fromUser(it) } ?: listOf()
    }
}