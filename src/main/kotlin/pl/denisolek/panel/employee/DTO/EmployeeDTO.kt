package pl.denisolek.panel.employee.DTO

import pl.denisolek.core.user.User

data class EmployeeDTO(
        var id: Int? = null,
        var firstName: String,
        var lastName: String,
        var email: String,
        var phoneNumber: String,
        var title: String? = "Pracownik"
) {
    companion object {
        fun fromUser(user: User) =
                EmployeeDTO(
                        id = user.id,
                        firstName = user.firstName!!,
                        lastName = user.lastName!!,
                        email = user.workEmail!!,
                        phoneNumber = user.phoneNumber!!
                )
    }
}