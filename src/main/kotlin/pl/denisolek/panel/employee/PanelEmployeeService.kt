package pl.denisolek.panel.employee

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.UserService
import pl.denisolek.panel.employee.DTO.CreateEmployeeDTO
import pl.denisolek.panel.employee.DTO.EmployeeDTO

@Service
class PanelEmployeeService(private val userService: UserService,
                           private val passwordEncoder: PasswordEncoder) {
    fun getEmployees(restaurant: Restaurant): List<EmployeeDTO> {
        return restaurant.employees?.map { EmployeeDTO.fromUser(it) } ?: listOf()
    }

    fun addEmployee(createEmployeeDTO: CreateEmployeeDTO, restaurant: Restaurant): List<EmployeeDTO> {
        val username = userService.generateUsername()
        val newEmployee = CreateEmployeeDTO.toUser(createEmployeeDTO).copy(
                username = username,
                email = "$username@mamstolik.pl",
                password = passwordEncoder.encode(createEmployeeDTO.pin),
                workPlace = restaurant
        )
        userService.save(newEmployee)
        return restaurant.employees?.map {
            EmployeeDTO.fromUser(it)
        } ?: listOf()
    }
}