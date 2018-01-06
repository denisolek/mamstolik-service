package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.SpecialDate
import java.time.LocalTime

data class OpenHoursDTO(
        var openTime: LocalTime,
        var closeTime: LocalTime,
        var isSpecialDate: Boolean = false
) {
    companion object {
        fun fromSpecialDate(specialDate: SpecialDate): OpenHoursDTO =
                OpenHoursDTO(
                        openTime = specialDate.businessHour.openTime,
                        closeTime = specialDate.businessHour.closeTime,
                        isSpecialDate = true
                )

        fun fromBusinessHour(businessHour: BusinessHour): OpenHoursDTO =
                OpenHoursDTO(
                        openTime = businessHour.openTime,
                        closeTime = businessHour.closeTime
                )
    }
}