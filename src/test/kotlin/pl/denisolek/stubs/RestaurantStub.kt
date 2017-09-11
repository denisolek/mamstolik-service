package pl.denisolek.stubs

import pl.denisolek.core.address.Address
import pl.denisolek.core.address.City
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime

class RestaurantStub {
    companion object {
        fun getRestaurantForStubs(): Restaurant =
                Restaurant(
                        name = "Test restaurant for stubs",
                        address = getAddress(),
                        avgReservationTime = Duration.ofMinutes(30),
                        description = "Test description for stubs",
                        isActive = true,
                        rate = 4.0f,
                        food_rate = 4.0f,
                        price_quality_rate = 4.0f,
                        service_rate = 4.0f,
                        facilities = getFacilities(),
                        kitchenTypes = getKitchenTypes(),
                        businessHours = getBusinessHours(),
                        spots = mutableListOf(),
                        reservations = mutableListOf()
                )

        fun getRestaurant(): Restaurant =
                Restaurant(
                        name = "Test restaurant",
                        address = getAddress(),
                        avgReservationTime = Duration.ofMinutes(30),
                        description = "Test description",
                        isActive = true,
                        rate = 4.0f,
                        food_rate = 4.0f,
                        price_quality_rate = 4.0f,
                        service_rate = 4.0f,
                        facilities = getFacilities(),
                        kitchenTypes = getKitchenTypes(),
                        businessHours = getBusinessHours(),
                        spots = getSpots(),
                        reservations = getReservations()
                )

        private fun getFacilities(): MutableSet<Restaurant.Facilities> =
                mutableSetOf(
                        Restaurant.Facilities.AIR_CONDITIONING,
                        Restaurant.Facilities.DARTS,
                        Restaurant.Facilities.PARKING,
                        Restaurant.Facilities.SPORTS_BROADCAST,
                        Restaurant.Facilities.ALCOHOL
                )

        private fun getKitchenTypes(): MutableSet<Restaurant.KitchenType> =
                mutableSetOf(
                        Restaurant.KitchenType.POLISH,
                        Restaurant.KitchenType.AMERICAN,
                        Restaurant.KitchenType.CAFE,
                        Restaurant.KitchenType.BURGERS,
                        Restaurant.KitchenType.ASIAN
                )

        private fun getBusinessHours(): MutableMap<DayOfWeek, BusinessHour> {
            val mondayThursday = BusinessHourStub.createBusinessHours(DayOfWeek.values().slice(IntRange(0, 3)), LocalTime.of(10, 0), LocalTime.of(20, 0))
            val saturdaySunday = BusinessHourStub.createBusinessHours(DayOfWeek.values().slice(IntRange(5, 6)), LocalTime.of(12, 0), LocalTime.of(18, 0))
            return mondayThursday.plus(saturdaySunday).toMutableMap()
        }

        private fun getAddress(): Address =
                Address(
                        streetName = "Test street name",
                        buildingNumber = "1A",
                        latitude = 34.669252f,
                        longitude = -41.105359f,
                        postalCode = "66-800",
                        city = City(name = "RestaurantStab city")
                )

        private fun getSpots(): MutableList<Spot> =
                mutableListOf(
                        Spot(5, getRestaurantForStubs()),
                        Spot(5, getRestaurantForStubs()),
                        Spot(5, getRestaurantForStubs()),
                        Spot(5, getRestaurantForStubs()),
                        Spot(2, getRestaurantForStubs()),
                        Spot(2, getRestaurantForStubs())
                )

        private fun getReservations(): MutableList<Reservation> =
                mutableListOf(
                        ReservationStub.createReservation().copy(),
                        ReservationStub.createReservation().copy(startTime = LocalTime.of(15, 0), endTime = LocalTime.of(15, 30), verificationCode = 222222),
                        ReservationStub.createReservation().copy(startTime = LocalTime.of(16, 0), endTime = LocalTime.of(16, 30), verificationCode = 333333)
                )
    }
}