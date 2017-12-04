package pl.denisolek.panel.restaurant.DTO.details

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import pl.denisolek.panel.image.DTO.ImageDTO
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import java.time.DayOfWeek

data class PanelRestaurantDetailsDTO(
        var name: String,
        var urlName: String,
        var description: String,
        var email: String,
        var phoneNumber: String,
        var type: RestaurantType,
        var address: Address,
        var businessHours: MutableMap<DayOfWeek, BusinessHour>,
        var specialDates: List<SpecialDateDTO>,
        var cuisineTypes: List<Restaurant.CuisineType>,
        var facilities: List<Restaurant.Facilities>,
        var menu: List<MenuCategoryDTO>,
        var images: List<ImageDTO>,
        var settings: Settings
)