package pl.denisolek.stubs.dto

import pl.denisolek.panel.employee.DTO.EmployeeDTO

class EmployeeDTOStub {
    companion object {
        fun getEmployeeDTO(): EmployeeDTO =
                EmployeeDTO(
                        firstName = "NameStub",
                        lastName = "SurnameStub",
                        email = "stub@test.pl",
                        phoneNumber = "111222333",
                        pin = "1111"
                )
    }
}