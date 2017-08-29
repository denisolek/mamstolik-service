package pl.denisolek.core.restaurant

import org.springframework.stereotype.Service

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {

    fun getAllRestaurants(): List<Restaurant> =
            restaurantRepository.findAll()
}