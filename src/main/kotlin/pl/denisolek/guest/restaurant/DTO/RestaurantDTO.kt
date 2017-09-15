package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.restaurant.Restaurant

data class RestaurantDTO(
        val id: Int?,
        val name: String) {

    companion object {
        fun fromRestaurantList(restaurants: List<Restaurant>): List<RestaurantDTO> =
                restaurants.map {
                    RestaurantDTO(
                            id = it.id,
                            name = it.name
                    )
                }

        fun fromRestaurant(restaurant: Restaurant): RestaurantDTO =
                RestaurantDTO(
                        id = restaurant.id,
                        name = restaurant.name
                )
    }
}