package pl.denisolek.shared.search

import org.springframework.stereotype.Service
import pl.denisolek.core.address.CityService
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.shared.search.dto.CitiesRestaurantsDTO
import pl.denisolek.shared.search.dto.RestaurantSearchDTO

@Service
class SearchService(private val cityService: CityService,
                    private val restaurantService: RestaurantService) {
    fun findCitiesAndRestaurants(name: String): CitiesRestaurantsDTO {
        val cities = cityService.findPartlyByNameOrAlias(name)
        val restaurants = RestaurantSearchDTO.fromRestaurantList(restaurantService.findPartlyByName(name))

        return CitiesRestaurantsDTO.fromCitiesRestaurantsList(cities, restaurants)
    }
}