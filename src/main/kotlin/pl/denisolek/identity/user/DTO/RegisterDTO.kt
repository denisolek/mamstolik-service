@file:Suppress("DEPRECATION")

package pl.denisolek.identity.user.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.pl.NIP
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterDTO(
        @field:Email
        val email: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "First name is not valid")
        var firstName: String,

        @field:NotBlank
        @field:Pattern(regexp = "^[\\p{L}\\s'.-]+$", message = "Last name is not valid")
        var lastName: String,

        @field:NotBlank
        var companyName: String,

        @field:NIP
        var nip: String,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"
    }
}