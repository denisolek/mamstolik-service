package pl.denisolek.guest.restaurant

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.address.City
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import java.time.LocalDateTime

@RestController
class GuestRestaurantController(val guestRestaurantService: GuestRestaurantService) : GuestRestaurantApi {
    override fun searchRestaurants(@RequestParam city: City,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam date: LocalDateTime,
                                   @RequestParam peopleNumber: Int): SearchDTO {
        return guestRestaurantService.searchRestaurants(city, date, peopleNumber)
    }
}