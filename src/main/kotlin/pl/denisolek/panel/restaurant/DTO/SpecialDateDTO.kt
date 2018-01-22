package pl.denisolek.panel.restaurant.DTO

import pl.denisolek.core.restaurant.BusinessHour
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
}