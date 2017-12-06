package pl.denisolek.panel.restaurant.DTO.baseInfo

import org.hibernate.validator.constraints.NotBlank
import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.SpecialDate
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import java.time.DayOfWeek
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class BaseInfoDTO(
        var settings: BaseInfoSettingsDTO,

        @field:Pattern(regexp = "^[a-z A-Z0-9&]+(-[a-z A-Z0-9&]+)?\$", message = "Name accepts a-z A-Z 0-9 - & and spaces.")
        @field:NotBlank
        var name: String,

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

        fun mapToExistingRestaurant(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): Restaurant {
            restaurant.settings?.localization = baseInfoDTO.settings.localization
            restaurant.settings?.specialDates = baseInfoDTO.settings.specialDates
            restaurant.name = baseInfoDTO.name
            restaurant.phoneNumber = baseInfoDTO.phoneNumber
            restaurant.type = baseInfoDTO.type
            restaurant.businessHours = getUpdatedBusinessHours(restaurant, baseInfoDTO)
            restaurant.specialDates = getUpdatedSpecialDates(baseInfoDTO, restaurant).toMutableList()
            restaurant.address = getUpdatedAddress(restaurant, baseInfoDTO)
            return restaurant
        }

        private fun getUpdatedAddress(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): Address {
            return Address(
                    id = restaurant.address.id,
                    streetName = baseInfoDTO.address.streetName,
                    buildingNumber = baseInfoDTO.address.buildingNumber,
                    postalCode = baseInfoDTO.address.postalCode,
                    latitude = baseInfoDTO.address.latitude,
                    longitude = baseInfoDTO.address.longitude
            )
        }

        private fun getUpdatedBusinessHours(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): Map<DayOfWeek, BusinessHour> {
            return DayOfWeek.values().map {
                restaurant.businessHours[it]?.openTime = baseInfoDTO.businessHours[it]?.openTime ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Invalid openTime for $it")
                restaurant.businessHours[it]?.closeTime = baseInfoDTO.businessHours[it]?.closeTime ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Invalid closeTime for $it")
                restaurant.businessHours[it]?.isClosed = baseInfoDTO.businessHours[it]?.isClosed ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Invalid isClosed for $it")
                Pair(it, restaurant.businessHours[it]!!)
            }.toMap()
        }

        private fun getUpdatedSpecialDates(baseInfoDTO: BaseInfoDTO, restaurant: Restaurant): List<SpecialDate> {
            return baseInfoDTO.specialDates.map { (id, date, businessHour) ->
                restaurant.specialDates.find { it.id == id }?.let {
                    it.businessHour = businessHour
                    it.date = date
                    it
                } ?: SpecialDate(
                        businessHour = businessHour,
                        date = date,
                        restaurant = restaurant
                )
            }
        }
    }
}
