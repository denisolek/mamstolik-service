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
import pl.denisolek.guest.restaurant.DTO.SpotInfoDTO
import pl.denisolek.infrastructure.API_BASE_PATH
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Api("Restaurant controller", tags = arrayOf("Restaurant"))
@RequestMapping(API_BASE_PATH)
interface GuestRestaurantApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val URL_NAME: String = "urlName"

        const val RESTAURANTS_BASE_PATH = "/restaurants"
        const val RESTAURANTS_URL_NAME_PATH = "$RESTAURANTS_BASE_PATH/{$URL_NAME:.+}"
        private const val RESTAURANTS_ID_PATH = "$RESTAURANTS_BASE_PATH/{$RESTAURANT_ID}"
        const val RESTAURANTS_ID_DATES_PATH = "$RESTAURANTS_ID_PATH/dates"
        const val RESTAURANTS_ID_SPOTS_PATH = "$RESTAURANTS_ID_PATH/spots"
    }

    @GetMapping(RESTAURANTS_BASE_PATH)
    fun searchRestaurants(@RequestParam city: City,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam date: LocalDateTime,
                          @RequestParam peopleNumber: Int): SearchDTO

    @GetMapping(RESTAURANTS_URL_NAME_PATH)
    fun getRestaurant(@ApiIgnore @PathVariable(URL_NAME) urlName: String): RestaurantDetailsDTO

    @GetMapping(RESTAURANTS_ID_DATES_PATH)
    fun getRestaurantAvailableDates(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam date: LocalDateTime,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam currentDate: LocalDateTime,
                                    @RequestParam peopleNumber: Int): Map<LocalDate, List<LocalTime>>

    @GetMapping(RESTAURANTS_ID_SPOTS_PATH)
    fun getRestaurantAvailableSpots(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam date: LocalDateTime,
                                    @RequestParam(required = false, defaultValue = "0") peopleNumber: Int): List<SpotInfoDTO>
}