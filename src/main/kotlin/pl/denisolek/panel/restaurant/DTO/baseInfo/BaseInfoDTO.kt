package pl.denisolek.panel.restaurant.DTO.baseInfo

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import java.time.DayOfWeek
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class BaseInfoDTO(
        var settings: BaseInfoSettingsDTO,

        @field:Pattern(regexp = "^[a-z A-Z0-9&]+(-[a-z A-Z0-9&]+)?\$", message = "Name accepts a-z A-Z 0-9 - & and spaces.")
        @field:NotBlank
        var name: String,

        @field:Size(max = 100, message = "Email too long.")
        @field:NotBlank
        @field:Email
        var email: String,

        @field:NotBlank
        @field:Size(min = 5)
        @field:Pattern(regexp = PHONE_MATCHER)
        var phoneNumber: String,

        var type: Restaurant.RestaurantType,

        var address: AddressDTO,

        var businessHours: MutableMap<DayOfWeek, BusinessHour>,

        var specialDates: List<SpecialDateDTO>
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"
    }
}
