package pl.denisolek.guest.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.RestaurantDTO
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import java.time.LocalDateTime

@Service
class GuestRestaurantService(val restaurantService: RestaurantService) {

    fun searchRestaurants(city: City, date: LocalDateTime, peopleNumber: Int): SearchDTO {
        val output: SearchDTO = SearchDTO.initSearchDTO()
        restaurantService.getActiveRestaurantsByCity(city)
                .map { restaurant ->
                    when (restaurant.getAvailability(date, peopleNumber)) {
                        Restaurant.AvailabilityType.AVAILABLE -> output.available.add(RestaurantDTO.fromRestaurant(restaurant))
                        Restaurant.AvailabilityType.POSSIBLE -> output.possible.add(RestaurantDTO.fromRestaurant(restaurant))
                        Restaurant.AvailabilityType.NOT_AVAILABLE -> output.notAvailable.add(RestaurantDTO.fromRestaurant(restaurant))
                        Restaurant.AvailabilityType.CLOSED -> output.closed.add(RestaurantDTO.fromRestaurant(restaurant))
                    }
                }
        return output
    }
}