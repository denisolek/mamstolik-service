package pl.denisolek.stubs.dto

import pl.denisolek.panel.employee.DTO.CreateEmployeeDTO

class CreateEmployeeDTOStub {
    companion object {
        fun getCreateEmployeeDTO(): CreateEmployeeDTO =
                CreateEmployeeDTO(
                        firstName = "NameStub",
                        lastName = "SurnameStub",
                        email = "stub@test.pl",
                        phoneNumber = "111222333",
                        pin = "1111"
                )
    }
}