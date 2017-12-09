package pl.denisolek.panel.restaurant.DTO

import org.springframework.http.HttpStatus
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.SpecialDate
import java.time.LocalDate

data class SpecialDateDTO(
        var id: Int? = null,
        var date: LocalDate,
        var businessHour: BusinessHour
) {
    companion object {
        fun fromSpecialDate(specialDate: SpecialDate) =
                SpecialDateDTO(
                        id = specialDate.id,
                        date = specialDate.date,
                        businessHour = specialDate.businessHour
                )
    }

    fun canBeAdded(restaurant: Restaurant): Boolean {
        restaurant.reservations.filter {
            it.startDateTime.toLocalDate() == date
        }.forEach {
            if (!it.isInsideBusinessHours(businessHour))
                throw ServiceException(HttpStatus.BAD_REQUEST, "Existing reservations doesn't fit in special date business hours.")
        }
        return true
    }
}