package pl.denisolek.guest.restaurant

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.guest.restaurant.DTO.RestaurantDetailsDTO
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import pl.denisolek.guest.restaurant.DTO.SpotDTO
import pl.denisolek.guest.restaurant.DTO.SpotInfoDTO
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RestController
class GuestRestaurantController(val guestRestaurantService: GuestRestaurantService) : GuestRestaurantApi {

    companion object {
        val API = GuestRestaurantApi.Companion
    }

    override fun searchRestaurants(@RequestParam city: City,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                   @RequestParam peopleNumber: Int): SearchDTO =
            guestRestaurantService.searchRestaurants(city, date, peopleNumber)

    override fun getRestaurant(@ApiIgnore @PathVariable(API.URL_NAME) urlName: String): RestaurantDetailsDTO =
            guestRestaurantService.getRestaurant(urlName)

    @ApiImplicitParams(
            ApiImplicitParam(name = "restaurantId", value = "Restaurant Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = "peopleNumber", value = "People number", paramType = "query", dataType = "int", required = true)
    )
    override fun getRestaurantAvailableDates(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                             @RequestParam peopleNumber: Int): Map<LocalDate, List<LocalTime>> =
            guestRestaurantService.getRestaurantAvailableDates(restaurantId, date, peopleNumber)

    @ApiImplicitParams(
            ApiImplicitParam(name = "restaurantId", value = "Restaurant Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = "peopleNumber", value = "People number", paramType = "query", dataType = "int", required = true)
    )
    override fun getRestaurantAvailableSpots(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                             @RequestParam(required = false, defaultValue = "0") peopleNumber: Int): List<SpotInfoDTO> =
            guestRestaurantService.getRestaurantAvailableSpots(restaurantId, date, peopleNumber)
}