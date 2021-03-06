package pl.denisolek.stubs

import pl.denisolek.core.customer.Customer
import pl.denisolek.core.floor.Floor
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.reservation.Reservation.ReservationState.PENDING
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.schema.SchemaItem
import pl.denisolek.core.spot.Spot
import java.time.Duration
import java.time.LocalDateTime

class FloorStub {
    companion object {
        fun getFloorStub(): Floor =
            Floor(
                id = 1,
                name = "Stubbed floor",
                restaurant = getRestaurant(),
                schemaItems = mutableListOf(
                    SchemaItem(
                        id = 1,
                        floor = getFloor(),
                        type = SchemaItem.Type.TABLE,
                        spot = getSpots()[0]
                    ),
                    SchemaItem(
                        id = 2,
                        floor = getFloor(),
                        type = SchemaItem.Type.TABLE,
                        spot = getSpots()[1]
                    ),
                    SchemaItem(
                        id = 3,
                        floor = getFloor(),
                        type = SchemaItem.Type.TABLE,
                        spot = getSpots()[2]
                    )
                )
            )

        fun getRestaurantWithReservations() = Restaurant(
            name = "Stubbed Restaurant",
            urlName = "stubbed.restaurant",
            type = Restaurant.RestaurantType.RESTAURANT,
            spots = getSpots(),
            reservations = mutableListOf(
                Reservation(
                    startDateTime = LocalDateTime.now().plusDays(1),
                    endDateTime = LocalDateTime.now().plusDays(1).plusMinutes(30),
                    spots = mutableListOf(getSpots()[0]),
                    restaurant = getStubbedRestaurant(),
                    customer = Customer(
                        email = "reservation@test.pl",
                        firstName = "Rezerwator",
                        lastName = "Rezerwujący",
                        phoneNumber = "123123123",
                        isVip = true
                    ),
                    duration = Duration.ofMinutes(30),
                    peopleNumber = 3,
                    state = PENDING,
                    verificationCode = "111111"
                ),
                Reservation(
                    startDateTime = LocalDateTime.now().plusDays(2),
                    endDateTime = LocalDateTime.now().plusDays(2).plusMinutes(30),
                    spots = mutableListOf(getSpots()[0]),
                    restaurant = getStubbedRestaurant(),
                    customer = Customer(
                        email = "reservation@test.pl",
                        firstName = "Rezerwator",
                        lastName = "Rezerwujący",
                        phoneNumber = "123123123",
                        isVip = true
                    ),
                    duration = Duration.ofMinutes(30),
                    peopleNumber = 3,
                    state = PENDING,
                    verificationCode = "111111"
                )
            ),
            email = "stubbed.restaurant@gmail.com",
            phoneNumber = "123123123"
        )

        private fun getFloor() = Floor(
            name = "Floor Stub",
            restaurant = getRestaurant()
        )

        private fun getRestaurant() = Restaurant(
            name = "Stubbed Restaurant",
            urlName = "stubbed.restaurant",
            type = Restaurant.RestaurantType.RESTAURANT,
            spots = getSpots(),
            email = "stubbed.restaurant@gmail.com",
            phoneNumber = "123123123"
        )

        private fun getSpots(): MutableList<Spot> = mutableListOf(
            Spot(
                id = 1,
                number = 1,
                restaurant = getStubbedRestaurant(),
                minPeopleNumber = 3,
                capacity = 6
            ),
            Spot(
                id = 2,
                number = 2,
                restaurant = getStubbedRestaurant(),
                minPeopleNumber = 3,
                capacity = 6
            ),
            Spot(
                id = 3,
                number = 3,
                restaurant = getStubbedRestaurant(),
                minPeopleNumber = 3,
                capacity = 6
            )
        )

        private fun getStubbedRestaurant() =
            Restaurant(
                name = "Stubbed Restaurant",
                urlName = "stubbed.restaurant",
                type = Restaurant.RestaurantType.RESTAURANT,
                email = "stubbed.restaurant@gmail.com",
                phoneNumber = "123123123"
            )
    }
}