package pl.denisolek.panel.employee

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.security.Authority
import pl.denisolek.core.security.Authority.Role.ROLE_EMPLOYEE
import pl.denisolek.core.security.Authority.Role.ROLE_MANAGER
import pl.denisolek.core.user.User
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
        if (createEmployeeDTO.pin.isNullOrBlank()) throw ServiceException(HttpStatus.BAD_REQUEST, "Only digits accepted in pin (4-10 length)")
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

    fun updateEmployee(createEmployeeDTO: CreateEmployeeDTO, restaurant: Restaurant, employee: User): List<EmployeeDTO> {
        val updatedEmployee = employee.copy(
                firstName = createEmployeeDTO.firstName,
                lastName = createEmployeeDTO.lastName,
                workEmail = createEmployeeDTO.email.toLowerCase(),
                phoneNumber = createEmployeeDTO.phoneNumber,
                password = when {
                    !createEmployeeDTO.pin.isNullOrBlank() -> passwordEncoder.encode(createEmployeeDTO.pin)
                    else -> employee.password
                },
                authorities = when {
                    createEmployeeDTO.isManager -> setOf(Authority(ROLE_EMPLOYEE), Authority(ROLE_MANAGER))
                    else -> setOf(Authority(ROLE_EMPLOYEE))
                }
        )
        userService.save(updatedEmployee)
        return restaurant.employees?.map {
            EmployeeDTO.fromUser(it)
        } ?: listOf()
    }
}