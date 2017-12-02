package pl.denisolek.core.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.address.City

@Service
class RestaurantService(private val restaurantRepository: RestaurantRepository) {
    fun getActiveRestaurantsByCity(city: City): List<Restaurant> =
            restaurantRepository.findByCityAndIsActive(city.id)

    fun findPartlyByName(name: String): List<Restaurant> =
            restaurantRepository.findPartlyByName(name)

    fun save(restaurant: Restaurant) =
            restaurantRepository.save(restaurant)

    fun generateUrlName(name: String): String {
        val urlName = name.replace(" ", ".").toLowerCase()
        var urlNameIdentifier = 1
        var exists = when (restaurantRepository.countByUrlName(urlName)) {
            0 -> false
            else -> {
                urlName.plus(".$urlNameIdentifier")
                true
            }
        }

        while (exists) when {
            restaurantRepository.countByUrlName(urlName) == 0 -> exists = false
            else -> {
                urlNameIdentifier++
                urlName.dropLastWhile { it == ".".single() }
                urlName.plus(".$urlNameIdentifier")
            }
        }
        return urlName
    }
}