package pl.denisolek.guest.restaurant

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.guest.restaurant.DTO.RestaurantDetailsDTO
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDateTime

@RestController
class GuestRestaurantController(val guestRestaurantService: GuestRestaurantService) : GuestRestaurantApi {

    companion object {
        val API = GuestRestaurantApi.Companion
    }

    override fun searchRestaurants(@RequestParam city: City,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                   @RequestParam peopleNumber: Int): SearchDTO =
            guestRestaurantService.searchRestaurants(city, date, peopleNumber)

    override fun getRestaurant(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurant: Restaurant): RestaurantDetailsDTO =
            RestaurantDetailsDTO.fromRestaurant(restaurant)
}