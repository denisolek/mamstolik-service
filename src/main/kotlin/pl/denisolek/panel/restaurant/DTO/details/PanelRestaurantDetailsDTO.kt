package pl.denisolek.panel.restaurant.DTO.details

import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import pl.denisolek.panel.image.DTO.ImageDTO
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import java.time.DayOfWeek

data class PanelRestaurantDetailsDTO(
        var name: String,
        var urlName: String,
        var description: String,
        var email: String,
        var phoneNumber: String,
        var type: RestaurantType,
        var address: AddressDTO,
        var businessHours: Map<DayOfWeek, BusinessHour>,
        var specialDates: List<SpecialDateDTO>,
        var cuisineTypes: List<Restaurant.CuisineType>,
        var facilities: List<Restaurant.Facilities>,
        var menu: List<MenuCategoryDTO>,
        var images: List<ImageDTO>,
        var settings: Settings
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant) =
                PanelRestaurantDetailsDTO(
                        name = restaurant.name,
                        urlName = restaurant.urlName,
                        description = restaurant.description,
                        email = restaurant.email,
                        phoneNumber = restaurant.phoneNumber,
                        type = restaurant.type,
                        address = AddressDTO.fromAddress(restaurant.address),
                        businessHours = restaurant.businessHours,
                        specialDates = restaurant.specialDates.map { SpecialDateDTO.fromSpecialDate(it) },
                        cuisineTypes = restaurant.cuisineTypes.toList(),
                        facilities = restaurant.facilities.toList(),
                        menu = restaurant.menu.categories.map { MenuCategoryDTO.fromMenuCategory(it) }.sortedBy { it.position },
                        images = restaurant.images.map { ImageDTO.fromImage(it) },
                        settings = restaurant.settings
                )
    }
}