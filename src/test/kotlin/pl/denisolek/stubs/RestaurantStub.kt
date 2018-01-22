package pl.denisolek.stubs

import pl.denisolek.core.address.Address
import pl.denisolek.core.address.City
import pl.denisolek.core.menu.Menu
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType.RESTAURANT
import pl.denisolek.core.security.Authority
import pl.denisolek.core.spot.Spot
import pl.denisolek.core.user.User
import java.time.*

class RestaurantStub {
    companion object {
        fun getRestaurantForStubs(): Restaurant =
            Restaurant(
                name = "Test restaurant for stubs",
                urlName = "test.restaurant.for.stubs",
                address = getAddress(),
                avgReservationTime = Duration.ofMinutes(30),
                description = "Test description for stubs",
                isActive = true,
                owner = getOwner(),
                rate = 4.0f,
                food_rate = 4.0f,
                price_quality_rate = 4.0f,
                service_rate = 4.0f,
                type = RESTAURANT,
                facilities = getFacilities(),
                cuisineTypes = getKitchenTypes(),
                businessHours = getBusinessHours(),
                spots = mutableListOf(),
                reservations = mutableListOf(),
                menu = Menu(),
                email = "stubbed.restaurant@gmail.com",
                phoneNumber = "123123123"
            )

        fun getRestaurant(): Restaurant =
            Restaurant(
                name = "Test restaurant",
                urlName = "test.restaurant",
                address = getAddress(),
                avgReservationTime = Duration.ofMinutes(30),
                description = "Test description",
                isActive = true,
                owner = getOwner(),
                rate = 4.0f,
                food_rate = 4.0f,
                price_quality_rate = 4.0f,
                service_rate = 4.0f,
                type = RESTAURANT,
                facilities = getFacilities(),
                cuisineTypes = getKitchenTypes(),
                businessHours = getBusinessHours(),
                spots = getSpots(),
                reservations = getReservations(),
                menu = Menu(),
                email = "stubbed.restaurant@gmail.com",
                phoneNumber = "123123123"
            )

        fun getRestaurantForAvailability(): Restaurant =
            Restaurant(
                name = "Test restaurant",
                urlName = "test.restaurant",
                address = getAddress(),
                avgReservationTime = Duration.ofMinutes(30),
                description = "Test description",
                isActive = true,
                owner = getOwner(),
                rate = 4.0f,
                food_rate = 4.0f,
                price_quality_rate = 4.0f,
                service_rate = 4.0f,
                type = RESTAURANT,
                facilities = getFacilities(),
                cuisineTypes = getKitchenTypes(),
                businessHours = getBusinessHours(),
                spots = getAvailabilitySpots(),
                reservations = getAvailabilityReservations(),
                menu = Menu(),
                email = "stubbed.restaurant@gmail.com",
                phoneNumber = "123123123"
            )

        private fun getOwner(): User =
            User(
                id = 1,
                firstName = "firstName",
                lastName = "lastName",
                phoneNumber = "111111111",
                username = "msOwner",
                email = "owner@mamstolik.pl",
                accountState = User.AccountState.ACTIVE,
                authorities = setOf(Authority(Authority.Role.ROLE_OWNER)),
                password = "password"
            )

        private fun getFacilities(): MutableSet<Restaurant.Facilities> =
            mutableSetOf(
                Restaurant.Facilities.AIR_CONDITIONING,
                Restaurant.Facilities.DARTS,
                Restaurant.Facilities.PARKING,
                Restaurant.Facilities.SPORTS_BROADCAST,
                Restaurant.Facilities.ALCOHOL
            )

        private fun getKitchenTypes(): MutableSet<Restaurant.CuisineType> =
            mutableSetOf(
                Restaurant.CuisineType.POLISH,
                Restaurant.CuisineType.AMERICAN,
                Restaurant.CuisineType.CAFE,
                Restaurant.CuisineType.BURGERS,
                Restaurant.CuisineType.ASIAN
            )

        private fun getBusinessHours(): MutableMap<DayOfWeek, BusinessHour> {
            val mondayThursday = BusinessHourStub.createBusinessHours(
                DayOfWeek.values().slice(IntRange(0, 3)),
                LocalTime.of(10, 0),
                LocalTime.of(20, 0)
            )
            val saturdaySunday = BusinessHourStub.createBusinessHours(
                DayOfWeek.values().slice(IntRange(5, 6)),
                LocalTime.of(12, 0),
                LocalTime.of(18, 0)
            )
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
                Spot(id = 1, capacity = 5, restaurant = getRestaurantForStubs(), number = 1),
                Spot(id = 2, capacity = 5, restaurant = getRestaurantForStubs(), number = 2),
                Spot(id = 3, capacity = 5, restaurant = getRestaurantForStubs(), number = 3),
                Spot(id = 4, capacity = 5, restaurant = getRestaurantForStubs(), number = 4),
                Spot(id = 5, capacity = 2, restaurant = getRestaurantForStubs(), number = 5),
                Spot(id = 6, capacity = 2, restaurant = getRestaurantForStubs(), number = 6)
            )

        private fun getReservations(): MutableList<Reservation> =
            mutableListOf(
                ReservationStub.createReservation().copy(
                    peopleNumber = 5,
                    spots = mutableListOf(Spot(id = 1, capacity = 5, restaurant = getRestaurantForStubs(), number = 1))
                ),
                ReservationStub.createReservation().copy(
                    peopleNumber = 3,
                    spots = mutableListOf(Spot(id = 2, capacity = 5, restaurant = getRestaurantForStubs(), number = 2)),
                    startDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(15, 0)),
                    endDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(15, 30)),
                    verificationCode = "222222"
                ),
                ReservationStub.createReservation().copy(
                    peopleNumber = 2,
                    spots = mutableListOf(Spot(id = 5, capacity = 2, restaurant = getRestaurantForStubs(), number = 5)),
                    startDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(16, 0)),
                    endDateTime = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(16, 30)),
                    verificationCode = "333333"
                )
            )

        private fun getAvailabilitySpots(): MutableList<Spot> =
            mutableListOf(
                Spot(id = 1, capacity = 5, minPeopleNumber = 5, restaurant = getRestaurantForStubs(), number = 1),
                Spot(id = 2, capacity = 2, minPeopleNumber = 2, restaurant = getRestaurantForStubs(), number = 2)
            )

        private fun getAvailabilityReservations(): MutableList<Reservation> =
            mutableListOf(
                ReservationStub.createReservation().copy(
                    peopleNumber = 5,
                    spots = mutableListOf(
                        Spot(
                            id = 1,
                            capacity = 5,
                            minPeopleNumber = 5,
                            restaurant = getRestaurantForStubs(),
                            number = 1
                        )
                    ),
                    startDateTime = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(14, 0)),
                    endDateTime = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(14, 30)),
                    verificationCode = "111111"
                ),
                ReservationStub.createReservation().copy(
                    peopleNumber = 2,
                    spots = mutableListOf(
                        Spot(
                            id = 2,
                            capacity = 2,
                            minPeopleNumber = 2,
                            restaurant = getRestaurantForStubs(),
                            number = 2
                        )
                    ),
                    startDateTime = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(14, 0)),
                    endDateTime = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(14, 30)),
                    verificationCode = "222222"
                )
            )
    }
}