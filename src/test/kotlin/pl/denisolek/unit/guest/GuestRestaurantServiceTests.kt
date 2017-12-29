package pl.denisolek.unit.guest

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.guest.restaurant.DTO.SpotInfoDTO
import pl.denisolek.guest.restaurant.GuestRestaurantService
import pl.denisolek.stubs.RestaurantStub
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(MockitoJUnitRunner::class)
class GuestRestaurantServiceTests {
    @InjectMocks
    lateinit var guestRestaurantService: GuestRestaurantService

    @Mock
    private val restaurantService = mock<RestaurantService>()

    @Test
    fun `getRestaurantAvailableSpots_ all available`() {
        val restaurant = RestaurantStub.getRestaurantForAvailability()
        restaurant.spots[0].minPeopleNumber = 2

        val actual = guestRestaurantService.getRestaurantAvailableSpots(
                restaurant = restaurant,
                date = LocalDateTime.of(LocalDate.of(2020, 10, 1), LocalTime.of(14, 0)),
                peopleNumber = 2
        )

        actual.forEach {
            Assert.assertEquals(SpotInfoDTO.SpotState.AVAILABLE, it.state)
        }
    }

    @Test
    fun `getRestaurantAvailableSpots_ one possible`() {
        val restaurant = RestaurantStub.getRestaurantForAvailability()

        val actual = guestRestaurantService.getRestaurantAvailableSpots(
                restaurant = restaurant,
                date = LocalDateTime.of(LocalDate.of(2020, 10, 1), LocalTime.of(14, 0)),
                peopleNumber = 2
        )

        Assert.assertEquals(SpotInfoDTO.SpotState.POSSIBLE, actual[0].state)
        Assert.assertEquals(SpotInfoDTO.SpotState.AVAILABLE, actual[1].state)
    }

    @Test
    fun `getRestaurantAvailableSpots_ all taken`() {
        val restaurant = RestaurantStub.getRestaurantForAvailability()

        val actual = guestRestaurantService.getRestaurantAvailableSpots(
                restaurant = restaurant,
                date = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(13, 45)),
                peopleNumber = 2
        )

        Assert.assertEquals(SpotInfoDTO.SpotState.TAKEN, actual[0].state)
        Assert.assertEquals(SpotInfoDTO.SpotState.TAKEN, actual[1].state)
    }

    @Test
    fun `getRestaurantAvailableSpots_ one taken`() {
        val restaurant = RestaurantStub.getRestaurantForAvailability()
        restaurant.reservations.removeIf { it.peopleNumber == 5 }

        val actual = guestRestaurantService.getRestaurantAvailableSpots(
                restaurant = restaurant,
                date = LocalDateTime.of(LocalDate.of(2018, 11, 29), LocalTime.of(13, 45)),
                peopleNumber = 5
        )

        Assert.assertEquals(SpotInfoDTO.SpotState.AVAILABLE, actual.find { it.id == 1 }!!.state)
        Assert.assertEquals(SpotInfoDTO.SpotState.TAKEN, actual.find { it.id == 2 }!!.state)
    }
}