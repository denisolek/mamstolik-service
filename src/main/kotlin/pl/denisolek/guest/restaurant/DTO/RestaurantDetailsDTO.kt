package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType
import java.time.DayOfWeek

data class RestaurantDetailsDTO(
        var id: Int? = null,
        var settings: GuestRestaurantSettingsDTO,
        var name: String,
        var type: RestaurantType,
        var description: String?,
        var rate: RestaurantRateDTO,
        var address: Address,
        var menu: List<MenuCategoryDTO>?,
        var tags: List<Any>,
        var businessHours: Map<DayOfWeek, BusinessHour>
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant): RestaurantDetailsDTO =
                RestaurantDetailsDTO(
                        id = restaurant.id,
                        settings = setSettings(restaurant),
                        name = restaurant.name,
                        type = restaurant.type,
                        description = setDescription(restaurant),
                        rate = setRate(restaurant),
                        address = restaurant.address,
                        menu = setMenu(restaurant),
                        businessHours = restaurant.businessHours,
                        tags = listOf(restaurant.cuisineTypes, restaurant.facilities)
                )

        private fun setSettings(restaurant: Restaurant): GuestRestaurantSettingsDTO {
            return GuestRestaurantSettingsDTO(
                    description = restaurant.settings!!.description,
                    localization = restaurant.settings!!.localization,
                    menu = restaurant.settings!!.menu
            )
        }

        private fun setDescription(restaurant: Restaurant) =
                if (restaurant.settings!!.description) restaurant.description else null

        private fun setRate(restaurant: Restaurant): RestaurantRateDTO {
            return RestaurantRateDTO(
                    total = restaurant.rate,
                    food_rate = restaurant.food_rate,
                    price_quality_rate = restaurant.price_quality_rate,
                    service_rate = restaurant.service_rate
            )
        }

        private fun setMenu(restaurant: Restaurant): List<MenuCategoryDTO>? {
            return if (restaurant.settings!!.menu)
                restaurant.menu?.categories?.map { MenuCategoryDTO.fromMenuCategory(it) }?.sortedBy { it.position }!!
            else
                null
        }
    }
}