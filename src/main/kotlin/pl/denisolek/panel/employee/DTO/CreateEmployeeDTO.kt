package pl.denisolek.panel.employee.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.security.Authority
import pl.denisolek.core.security.Authority.Role.ROLE_EMPLOYEE
import pl.denisolek.core.security.Authority.Role.ROLE_MANAGER
import pl.denisolek.core.user.User
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateEmployeeDTO(
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

        @field:Pattern(regexp = "^(?!\\s*$)[0-9\\s]{4,10}$", message = "Only digits accepted in pin (4-10 length)")
        var pin: String? = null,

        var isManager: Boolean = false
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"

        fun toUser(createEmployeeDTO: CreateEmployeeDTO) =
                User(
                        email = createEmployeeDTO.email.toLowerCase(),
                        firstName = createEmployeeDTO.firstName,
                        lastName = createEmployeeDTO.lastName,
                        workEmail = createEmployeeDTO.email.toLowerCase(),
                        phoneNumber = createEmployeeDTO.phoneNumber,
                        accountState = User.AccountState.ACTIVE,
                        authorities = when {
                            createEmployeeDTO.isManager -> setOf(Authority(ROLE_EMPLOYEE), Authority(ROLE_MANAGER))
                            else -> setOf(Authority(ROLE_EMPLOYEE))
                        }
                )
    }
}