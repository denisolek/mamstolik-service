package pl.denisolek.panel.employee.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User
import pl.denisolek.panel.identity.DTO.RegisterDTO
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class EmployeeDTO(
        var id: Int? = null,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "First name is not valid")
        var firstName: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "Last name is not valid")
        var lastName: String,

        @field:Size(max = 100, message = "Email too long.")
        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String,

        @field:NotBlank
        @field:Pattern(regexp = "^(?!\\s*$)[0-9\\s]{4,10}$", message = "Only digits accepted in pin (4-10 length)")
        var pin: String? = null,

        var title: String? = "Pracownik"
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"

        fun fromUser(user: User) =
                EmployeeDTO(
                        id = user.id,
                        firstName = user.firstName!!,
                        lastName = user.lastName!!,
                        email = user.workEmail!!,
                        phoneNumber = user.phoneNumber!!
                )

        fun toUser(employeeDTO: EmployeeDTO) =
                User(
                        firstName = employeeDTO.firstName,
                        lastName = employeeDTO.lastName,
                        workEmail = employeeDTO.email,
                        phoneNumber = employeeDTO.phoneNumber,
                        authorities = setOf(Authority(Authority.Role.ROLE_EMPLOYEE)),
                        accountState = User.AccountState.ACTIVE,
                        title = employeeDTO.title
                )
    }
}