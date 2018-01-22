package pl.denisolek.guest.restaurant

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.RestaurantDetailsDTO
import pl.denisolek.guest.restaurant.DTO.RestaurantSearchDTO
import pl.denisolek.guest.restaurant.DTO.SearchDTO
import pl.denisolek.guest.restaurant.DTO.SpotInfoDTO
import pl.denisolek.panel.schema.DTO.SchemaDTO
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class GuestRestaurantService(val restaurantService: RestaurantService) {

    fun searchRestaurants(city: City, date: LocalDateTime, peopleNumber: Int): SearchDTO {
        val output: SearchDTO = SearchDTO.initSearchDTO()
        restaurantService.getActiveRestaurantsByCity(city)
            .map { restaurant ->
                when (restaurant.getAvailability(date, peopleNumber)) {
                    Restaurant.AvailabilityType.AVAILABLE -> output.available.add(
                        RestaurantSearchDTO.fromRestaurant(
                            restaurant
                        )
                    )
                    Restaurant.AvailabilityType.POSSIBLE -> output.possible.add(
                        RestaurantSearchDTO.fromRestaurant(
                            restaurant
                        )
                    )
                    Restaurant.AvailabilityType.NOT_AVAILABLE -> output.notAvailable.add(
                        RestaurantSearchDTO.fromRestaurant(
                            restaurant
                        )
                    )
                    Restaurant.AvailabilityType.CLOSED -> output.closed.add(
                        RestaurantSearchDTO.fromRestaurant(
                            restaurant
                        )
                    )
                }
            }
        return output
    }

    fun getRestaurantAvailableDates(
        restaurant: Restaurant,
        date: LocalDateTime,
        currentDate: LocalDateTime,
        peopleNumber: Int
    ): Map<LocalDate, List<LocalTime>> =
        restaurant.getAvailableDates(date, currentDate, peopleNumber)

    fun getRestaurantAvailableSpots(restaurant: Restaurant, date: LocalDateTime, peopleNumber: Int): List<SpotInfoDTO> {
        if (!restaurant.isOpenAt(date))
            return restaurant.spots.map { SpotInfoDTO(it.id!!, SpotInfoDTO.SpotState.NOT_AVAILABLE) }

        val takenSpots = restaurant.getTakenSpotsAt(date)
        return restaurant.spots.map {
            when (peopleNumber) {
                0 -> {
                    when {
                        takenSpots.contains(it) -> SpotInfoDTO(it.id!!, SpotInfoDTO.SpotState.TAKEN)
                        else -> SpotInfoDTO(it.id!!, SpotInfoDTO.SpotState.AVAILABLE)
                    }
                }
                else -> {
                    when {
                        takenSpots.contains(it) -> SpotInfoDTO(it.id!!, SpotInfoDTO.SpotState.TAKEN)
                        it.capacity >= peopleNumber && it.minPeopleNumber <= peopleNumber -> SpotInfoDTO(
                            it.id!!,
                            SpotInfoDTO.SpotState.AVAILABLE
                        )
                        it.capacity >= peopleNumber && it.minPeopleNumber > peopleNumber -> SpotInfoDTO(
                            it.id!!,
                            SpotInfoDTO.SpotState.POSSIBLE
                        )
                        else -> SpotInfoDTO(it.id!!, SpotInfoDTO.SpotState.NOT_AVAILABLE)
                    }
                }
            }
        }
    }

    fun getRestaurant(urlName: String): RestaurantDetailsDTO {
        val restaurant = restaurantService.findByUrlName(urlName) ?: throw ServiceException(
            HttpStatus.NOT_FOUND,
            "Restaurant not found"
        )
        return RestaurantDetailsDTO.fromRestaurant(restaurant)
    }

    fun getSchema(restaurant: Restaurant): SchemaDTO {
        return SchemaDTO(restaurant)
    }
}