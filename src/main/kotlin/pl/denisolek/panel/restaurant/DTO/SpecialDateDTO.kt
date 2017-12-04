package pl.denisolek.panel.restaurant.DTO

import pl.denisolek.core.restaurant.BusinessHour
import java.time.LocalDate

data class SpecialDateDTO(
        var id: Int? = null,
        var date: LocalDate,
        var businessHour: BusinessHour
)