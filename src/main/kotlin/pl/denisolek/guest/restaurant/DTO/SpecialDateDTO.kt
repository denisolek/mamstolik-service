package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.restaurant.SpecialDate

data class SpecialDateDTO(
    var id: Int? = null,
    var description: String? = ""
) {
    companion object {
        fun fromSpecialDate(specialDate: SpecialDate): SpecialDateDTO =
            SpecialDateDTO(
                id = specialDate.id,
                description = specialDate.description
            )
    }
}