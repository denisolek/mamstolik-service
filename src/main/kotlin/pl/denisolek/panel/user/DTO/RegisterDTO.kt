package pl.denisolek.panel.user.DTO

import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.security.Authority
import pl.denisolek.core.user.User
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterDTO(

        @field:Size(max = 100, message = "Email too long.")
        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "First name is not valid")
        var firstName: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "Last name is not valid")
        var lastName: String,

        @field:NotBlank
        var companyName: String,

        @field:NotBlank
        var nip: String,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
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
                    authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                    activationKey = RandomStringUtils.randomAlphanumeric(30)
            )
}