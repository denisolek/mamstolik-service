package pl.denisolek

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.denisolek.core.address.Address
import pl.denisolek.core.address.City
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantRepository
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import javax.annotation.PostConstruct

@Component
@Profile("initDb")
class InitDb(val restaurantRepository: RestaurantRepository) {

    @PostConstruct
    fun init() {
//        val restaurant = createRestaurant()
//        restaurantRepository.save(restaurant)
    }

    private fun createRestaurant() = Restaurant(
            name = "Test restaurant",
            address = Address(
                    streetName = "Test street name",
                    buildingNumber = "1A",
                    latitude = 34.669252f,
                    longitude = -41.105359f,
                    postalCode = "66-800",
                    city = City(name = "RestaurantStab city")
            ),
            avgReservationTime = Duration.ofMinutes(30),
            description = "Test description",
            isActive = true,
            rate = 4.0f,
            food_rate = 4.0f,
            price_quality_rate = 4.0f,
            service_rate = 4.0f,
            businessHours = mutableMapOf(
                    Pair(DayOfWeek.MONDAY, BusinessHour(LocalTime.of(10, 0), LocalTime.of(18, 0))),
                    Pair(DayOfWeek.TUESDAY, BusinessHour(LocalTime.of(10, 0), LocalTime.of(14, 0)))
            )
    )
}