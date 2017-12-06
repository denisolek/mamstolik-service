package pl.denisolek.stubs.dto

import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.restaurant.DTO.SpecialDateDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.AddressDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoSettingsDTO
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

class BaseInfoDTOStub {
    companion object {
        fun getBaseInfoDTO(): BaseInfoDTO = BaseInfoDTO(
                settings = BaseInfoSettingsDTO(false, false),
                name = "Updated Name",
                phoneNumber = "333222111",
                type = Restaurant.RestaurantType.BAR,
                businessHours = DayOfWeek.values().map { dayOfWeek ->
                    Pair(dayOfWeek, BusinessHour())
                }.toMap().toMutableMap(),
                address = AddressDTO(
                        streetName = "updatedStreetName",
                        buildingNumber = "updatedBuildingNumber",
                        postalCode = "updatePostalCode",
                        city = "updatedCity",
                        latitude = 1000f,
                        longitude = 1000f
                ),
                specialDates = listOf(
                        SpecialDateDTO(
                                id = 1,
                                date = LocalDate.of(2017, 10, 10),
                                businessHour = BusinessHour(
                                        id = 50,
                                        openTime = LocalTime.of(15,20),
                                        closeTime = LocalTime.of(15, 20),
                                        isClosed = false
                                )
                        ),
                        SpecialDateDTO(
                                id = 2,
                                date = LocalDate.of(2017, 10, 17),
                                businessHour = BusinessHour(
                                        id = 51,
                                        openTime = LocalTime.of(15,20),
                                        closeTime = LocalTime.of(15, 20),
                                        isClosed = false
                                )
                        )
                )
        )
    }
}