package pl.denisolek.core.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.address.City

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {
    fun getActiveRestaurantsByCity(city: City): List<Restaurant> =
            restaurantRepository.findByCityAndIsActive(city.id)
}