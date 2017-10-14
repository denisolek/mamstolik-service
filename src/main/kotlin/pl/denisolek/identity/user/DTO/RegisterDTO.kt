@file:Suppress("DEPRECATION")

package pl.denisolek.identity.user.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterDTO(
        @Email
        var email: String,

        @NotBlank
        @Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "First name is not valid")
        var firstName: String,

        @NotBlank
        @Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "Last name is not valid")
        var lastName: String,

        @NotBlank
        var companyName: String,

        var nip: String,

        @NotBlank
        @Size(min = 5)
        @Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"
    }

    fun toUser(): User =
            User(
                    email = this.email,
                    firstName = this.firstName,
                    lastName = this.lastName,
                    companyName = this.companyName,
                    nip = this.nip,
                    phoneNumber = this.phoneNumber,
                    accountState = User.AccountState.NOT_ACTIVE,
                    authorities = setOf(Authority(Authority.Role.ROLE_OWNER))
    )
}