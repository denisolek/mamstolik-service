package pl.denisolek.guest.restaurant

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.RestaurantDTO
import java.time.LocalDateTime

@RestController
class GuestRestaurantController(val restaurantService: RestaurantService) : GuestRestaurantApi {
    override fun searchRestaurants(@RequestParam city: String,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                   @RequestParam peopleNumber: Int): List<RestaurantDTO> {
        return RestaurantDTO.fromRestaurantList(restaurantService.getRestaurantByCity(city))
    }
}