package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import java.time.DayOfWeek

data class RestaurantDetailsDTO(
        var id: Int? = null,
        var name: String,
        var description: String,
        var rate: RestaurantRateDTO,
        var address: Address,
        var menu: List<MenuCategoryDTO>,
        var tags: List<Any>,
        var businessHours: MutableMap<DayOfWeek, BusinessHour>
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant): RestaurantDetailsDTO =
                RestaurantDetailsDTO(
                        id = restaurant.id,
                        name = restaurant.name,
                        description = restaurant.description,
                        rate = RestaurantRateDTO(
                                total = restaurant.rate,
                                food_rate = restaurant.food_rate,
                                price_quality_rate = restaurant.price_quality_rate,
                                service_rate = restaurant.service_rate
                        ),
                        address = restaurant.address,
                        menu = restaurant.menu.categories.map { MenuCategoryDTO.fromMenuCategory(it) }.sortedBy { it.position },
                        businessHours = restaurant.businessHours,
                        tags = listOf(restaurant.cuisineTypes, restaurant.facilities)
                )
    }
}