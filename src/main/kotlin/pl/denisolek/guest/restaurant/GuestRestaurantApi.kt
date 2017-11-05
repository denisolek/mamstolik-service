package pl.denisolek.guest.restaurant

import io.swagger.annotations.Api
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.guest.restaurant.DTO.RestaurantDetailsDTO
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import pl.denisolek.infrastructure.API_BASE_PATH
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDateTime

@Api("Restaurant controller", tags = arrayOf("Restaurant"))
@RequestMapping(API_BASE_PATH)
interface GuestRestaurantApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val RESTAURANTS_BASE_PATH = "/restaurants"
        const val GET_RESTAURANT_PATH = "$RESTAURANTS_BASE_PATH/{$RESTAURANT_ID}"
    }

    @GetMapping(RESTAURANTS_BASE_PATH)
    fun searchRestaurants(@RequestParam city: City,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam date: LocalDateTime,
                          @RequestParam peopleNumber: Int): SearchDTO

    @GetMapping(GET_RESTAURANT_PATH)
    fun getRestaurant(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurant: Restaurant): RestaurantDetailsDTO
}