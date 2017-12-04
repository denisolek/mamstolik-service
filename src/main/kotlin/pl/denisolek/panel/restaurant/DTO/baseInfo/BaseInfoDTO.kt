package pl.denisolek.panel.restaurant.DTO.baseInfo

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import java.time.DayOfWeek

data class BaseInfoDTO(
        var settings: BaseInfoSettingsDTO,
        var name: String,
        var urlName: String,
        var email: String,
        var phoneNumber: String,
        var type: Restaurant.RestaurantType,
        var address: Address,
        var businessHours: MutableMap<DayOfWeek, BusinessHour>,
        var specialDates: List<SpecialDateDTO>
)
