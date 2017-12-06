package pl.denisolek.stubs.dto

import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.CuisineType.ITALIAN
import pl.denisolek.core.restaurant.Restaurant.CuisineType.POLISH
import pl.denisolek.core.restaurant.Restaurant.Facilities.AIR_CONDITIONING
import pl.denisolek.core.restaurant.Restaurant.Facilities.SMOKING_ROOM
import pl.denisolek.core.restaurant.Settings
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

class PanelRestaurantDetailsDTOStub {
    companion object {
        fun getUpdatedBaseInfo() = PanelRestaurantDetailsDTO(
                name = "Updated Name",
                urlName = "updated.name",
                description = "",
                email = "pianobar@gmail.com",
                phoneNumber = "333222111",
                type = Restaurant.RestaurantType.BAR,
                address = AddressDTO(
                        streetName = "updatedStreetName",
                        buildingNumber = "updatedBuildingNumber",
                        postalCode = "updatePostalCode",
                        city = "updatedCity",
                        latitude = 1000f,
                        longitude = 1000f
                ),
                businessHours = DayOfWeek.values().map { dayOfWeek ->
                    Pair(dayOfWeek, BusinessHour())
                }.toMap().toMutableMap(),
                cuisineTypes = listOf(POLISH, ITALIAN),
                facilities = listOf(SMOKING_ROOM, AIR_CONDITIONING),
                menu = listOf(),
                images = listOf(),
                settings = Settings(localization = false, specialDates = false),
                specialDates = listOf(
                        SpecialDateDTO(
                                id = 1,
                                date = LocalDate.of(2017, 10, 10),
                                businessHour = BusinessHour(
                                        id = 50,
                                        openTime = LocalTime.of(15, 20),
                                        closeTime = LocalTime.of(15, 20),
                                        isClosed = false
                                )
                        ),
                        SpecialDateDTO(
                                id = 2,
                                date = LocalDate.of(2017, 10, 17),
                                businessHour = BusinessHour(
                                        id = 51,
                                        openTime = LocalTime.of(15, 20),
                                        closeTime = LocalTime.of(15, 20),
                                        isClosed = false
                                )
                        )
                )
        )
    }
}