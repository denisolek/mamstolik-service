package pl.denisolek.panel.identity.DTO

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.restaurant.Restaurant.RestaurantType
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreateRestaurantDTO(
        @field:Pattern(regexp = "^[a-z A-Z0-9&]+(-[a-z A-Z0-9&]+)?\$", message = "Name accepts a-z A-Z 0-9 - & and spaces.")
        @field:NotBlank
        var name: String,

        @field:Size(max = 100, message = "Email too long.")
        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotNull
        var type: RestaurantType,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String,

        @field:NotBlank
        @field:Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,50})", message = "Password requires 8-50 length and at least one of each: a-z, A-Z, 1-9")
        var password: String
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"
    }
}