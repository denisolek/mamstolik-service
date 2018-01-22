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
            email = "updated@email.com",
            type = Restaurant.RestaurantType.BAR,
            businessHours = mutableMapOf(
                Pair(DayOfWeek.MONDAY, BusinessHour(1, LocalTime.of(13, 0), LocalTime.of(21, 0), false)),
                Pair(DayOfWeek.TUESDAY, BusinessHour(2, LocalTime.of(12, 0), LocalTime.of(21, 0), false)),
                Pair(DayOfWeek.WEDNESDAY, BusinessHour(3, LocalTime.of(12, 0), LocalTime.of(21, 0), false)),
                Pair(DayOfWeek.THURSDAY, BusinessHour(4, LocalTime.of(12, 0), LocalTime.of(21, 0), false)),
                Pair(DayOfWeek.FRIDAY, BusinessHour(5, LocalTime.of(13, 0), LocalTime.of(23, 0), false)),
                Pair(DayOfWeek.SATURDAY, BusinessHour(6, LocalTime.of(13, 0), LocalTime.of(23, 0), false)),
                Pair(DayOfWeek.SUNDAY, BusinessHour(7, LocalTime.of(13, 0), LocalTime.of(20, 0), false))
            ),
            address = AddressDTO(
                streetName = "updatedStreetName",
                buildingNumber = "updatedBuildingNumber",
                postalCode = "updatePostalCode",
                city = "updatedCity",
                latitude = 1000f,
                longitude = 1000f
            ),
            specialDates = mutableListOf(
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
                ),
                SpecialDateDTO(
                    id = 3,
                    date = LocalDate.of(2018, 10, 23),
                    businessHour = BusinessHour(
                        id = 52,
                        openTime = LocalTime.of(1, 0),
                        closeTime = LocalTime.of(6, 0),
                        isClosed = false
                    )
                )
            )
        )
    }
}