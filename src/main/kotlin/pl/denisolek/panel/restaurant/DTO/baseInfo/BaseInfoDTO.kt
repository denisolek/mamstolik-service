package pl.denisolek.panel.restaurant.DTO.baseInfo

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.SpecialDate
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import java.time.DayOfWeek
import javax.validation.Valid
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class BaseInfoDTO(
        var settings: BaseInfoSettingsDTO,

        @field:Pattern(regexp = "^[a-z A-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ]+(-[a-z A-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ]+)?\$", message = "Name accepts a-z A-Z 0-9 - & and spaces.")
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

        @field:Valid
        var address: AddressDTO,

        var businessHours: MutableMap<DayOfWeek, BusinessHour>,

        var specialDates: MutableList<SpecialDateDTO>
) {
    companion object {
        internal const val PHONE_MATCHER = "(\\(?\\+[\\d]{2}\\(?)?([ .-]?)([0-9]{3})([ .-]?)([0-9]{3})\\4([0-9]{3})"

        fun mapToExistingRestaurant(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): Restaurant {
            restaurant.settings?.localization = baseInfoDTO.settings.localization
            restaurant.settings?.specialDates = baseInfoDTO.settings.specialDates
            restaurant.name = baseInfoDTO.name
            restaurant.phoneNumber = baseInfoDTO.phoneNumber
            restaurant.type = baseInfoDTO.type
            restaurant.email = baseInfoDTO.email

            updateAddress(restaurant, baseInfoDTO)
            updateSpecialDates(restaurant, baseInfoDTO)
            updateBusinessHours(restaurant, baseInfoDTO)
            validateReservations(restaurant)
            return restaurant
        }

        private fun updateAddress(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO) {
            restaurant.address.streetName = baseInfoDTO.address.streetName
            restaurant.address.buildingNumber = baseInfoDTO.address.buildingNumber
            restaurant.address.postalCode = baseInfoDTO.address.postalCode
            restaurant.address.latitude = baseInfoDTO.address.latitude
            restaurant.address.longitude = baseInfoDTO.address.longitude
        }

        private fun updateSpecialDates(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO) {
            restaurant.specialDates.removeIf { specialDate ->
                !baseInfoDTO.specialDates.any {
                    it.id == specialDate.id
                }
            }

            baseInfoDTO.specialDates.forEach { (id, date, businessHour) ->
                var existing = false
                if (businessHour.closeTime.isBefore(businessHour.openTime))
                    throw ServiceException(HttpStatus.BAD_REQUEST, "Close time must be greater than open time.")

                restaurant.specialDates.map {
                    when {
                        (id == it.id && date != it.date) -> throw ServiceException(HttpStatus.BAD_REQUEST, "You can't edit date ($date) of existing special day (id ${it.id}).")
                        (id != it.id && date == it.date) -> throw ServiceException(HttpStatus.CONFLICT, "Special date for that day already exists (id ${it.id})")
                        (id == it.id && date == it.date) -> {
                            it.businessHour.openTime = businessHour.openTime
                            it.businessHour.closeTime = businessHour.closeTime
                            it.businessHour.isClosed = businessHour.isClosed
                            it.date = date
                            existing = true
                        }
                    }
                }

                if (!existing) {
                    restaurant.specialDates.add(SpecialDate(
                            date = date,
                            restaurant = restaurant,
                            businessHour = BusinessHour(
                                    openTime = businessHour.openTime,
                                    closeTime = businessHour.closeTime,
                                    isClosed = businessHour.isClosed
                            ))
                    )
                }
            }
        }

        private fun updateBusinessHours(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO) {
            restaurant.businessHours.forEach {
                val dtoBusinessHour = baseInfoDTO.businessHours[it.key] ?: throw ServiceException(HttpStatus.BAD_REQUEST, "Invalid businessHours for ${it.key}")
                if (dtoBusinessHour.closeTime.isBefore(dtoBusinessHour.openTime))
                    throw ServiceException(HttpStatus.BAD_REQUEST, "Close time for ${it.key} must be greater than open time.")
                it.value.openTime = dtoBusinessHour.openTime
                it.value.closeTime = dtoBusinessHour.closeTime
                it.value.isClosed = dtoBusinessHour.isClosed
            }
        }

        private fun validateReservations(restaurant: Restaurant) {
            restaurant.reservations.forEach {
                it.validate()
            }
        }
    }
}
