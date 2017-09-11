package pl.denisolek.guest.restaurant

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.RestaurantDTO
import java.time.LocalDateTime

@RestController
class GuestRestaurantController(val restaurantService: RestaurantService) : GuestRestaurantApi {
    override fun searchRestaurants(@RequestParam city: City,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                   @RequestParam peopleNumber: Int): List<RestaurantDTO> {

        val restaurants = restaurantService.getActiveRestaurantsByCity(city)
                .filter { restaurant ->
                    restaurant.isOpenAt(date)
                }

        return RestaurantDTO.fromRestaurantList(restaurants)
    }
}