package pl.denisolek.shared.search.dto

import pl.denisolek.core.address.City

data class CitiesRestaurantsDTO(
        val cities: List<City>,
        val restaurants: List<RestaurantSearchDTO>
) {
    companion object {
        fun fromCitiesRestaurantsList(cities: List<City>, restaurants: List<RestaurantSearchDTO>): CitiesRestaurantsDTO =
                CitiesRestaurantsDTO(
                        cities = cities.sortedBy { it.name }.take(4),
                        restaurants = restaurants.sortedBy { it.name }.take(4))
    }
}