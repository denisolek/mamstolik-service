package pl.denisolek.shared.search.dto

import pl.denisolek.core.restaurant.Restaurant

data class RestaurantSearchDTO(
        val id: Int?,
        val name: String) {
    companion object {
        fun fromRestaurantList(restaurants: List<Restaurant>): List<RestaurantSearchDTO> =
                restaurants.map {
                    RestaurantSearchDTO(
                            id = it.id,
                            name = it.name
                    )
                }
    }
}