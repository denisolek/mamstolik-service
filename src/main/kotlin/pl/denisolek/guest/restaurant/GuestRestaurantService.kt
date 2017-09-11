package pl.denisolek.guest.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.RestaurantDTO
import java.time.LocalDateTime

@Service
class GuestRestaurantService(val restaurantService: RestaurantService) {

    fun searchRestaurants(city: City, date: LocalDateTime, peopleNumber: Int): List<RestaurantDTO> {
        val restaurants = restaurantService.getActiveRestaurantsByCity(city)
                .filter { restaurant ->
                    restaurant.isOpenAt(date)
                }
        return RestaurantDTO.fromRestaurantList(restaurants)
    }
}