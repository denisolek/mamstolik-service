package pl.denisolek.unit.core.restaurant

import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.spot.Spot
import pl.denisolek.stubs.RestaurantStub
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@RunWith(MockitoJUnitRunner::class)
class RestaurantTest {

    @Test
    fun `isOpenAt_ date inside businessHours`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(15, 0)))

        Assert.assertTrue(actual)
    }

    @Test
    fun `isOpenAt_ date equal openTime`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(10, 0)))

        Assert.assertTrue(actual)
    }

    @Test
    fun `isOpenAt_ date equal closeTime`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(20, 0)))

        Assert.assertFalse(actual)
    }

    @Test
    fun `isOpenAt_ date equal closeTime - avgReservationTime`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(19, 30)))

        Assert.assertTrue(actual)
    }

    @Test
    fun `isOpenAt_ date after closeTime`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(21, 0)))

        Assert.assertFalse(actual)
    }

    @Test
    fun `isOpenAt_ date before openTime`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 3), LocalTime.of(9, 0)))

        Assert.assertFalse(actual)
    }

    @Test
    fun `isOpenAt_ restaurant closed`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.isOpenAt(LocalDateTime.of(LocalDate.of(2017, 10, 6), LocalTime.of(15, 0)))

        Assert.assertFalse(actual)
    }

    @Test
    fun `getAvailableSpotsAt_ before reservation`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(13, 0)))

        Assert.assertEquals(6, actual.size)
    }

    @Test
    fun `getAvailableSpotsAt_ start before reservation, end equal reservationStart`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(13, 30)))

        Assert.assertEquals(6, actual.size)
    }

    @Test
    fun `getAvailableSpotsAt_ start before reservation, end inside reservation`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(13, 40)))

        Assert.assertEquals(5, actual.size)
        Assert.assertTrue(!actual.contains(Spot(id = 1, capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs())))
    }

    @Test
    fun `getAvailableSpotsAt_ equal reservation`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(14, 0)))

        Assert.assertEquals(5, actual.size)
        Assert.assertTrue(!actual.contains(Spot(id = 1, capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs())))
    }

    @Test
    fun `getAvailableSpotsAt_ start inside reservation, end after reservation`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(14, 10)))

        Assert.assertEquals(5, actual.size)
        Assert.assertTrue(!actual.contains(Spot(id = 1, capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs())))
    }

    @Test
    fun `getAvailableSpotsAt_ start equal reservationEnd, end after reservation`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(14, 30)))

        Assert.assertEquals(6, actual.size)
    }

    @Test
    fun `getAvailableSpotsAt_ different spot`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailableSpotsAt(LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(16, 10)))

        Assert.assertEquals(5, actual.size)
        Assert.assertTrue(!actual.contains(Spot(id = 5, capacity = 2, restaurant = RestaurantStub.getRestaurantForStubs())))
    }

    @Test
    fun `getAvailability_ CLOSED`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailability(
                date = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(4, 0)),
                peopleNumber = 4
        )

        Assert.assertThat(actual, `is`(Restaurant.AvailabilityType.CLOSED))
    }

    @Test
    fun `getAvailability_ AVAILABLE`() {
        val restaurant = RestaurantStub.getRestaurant()
        restaurant.spots = mutableListOf(
                Spot(id = 1, capacity = 5, minPeopleNumber = 3, restaurant = RestaurantStub.getRestaurantForStubs())
        )
        val actual = restaurant.getAvailability(
                date = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(11, 0)),
                peopleNumber = 3
        )

        Assert.assertThat(actual, `is`(Restaurant.AvailabilityType.AVAILABLE))
    }

    @Test
    fun `getAvailability_ POSSIBLE`() {
        val restaurant = RestaurantStub.getRestaurant()
        restaurant.spots = mutableListOf(
                Spot(id = 1, capacity = 5, minPeopleNumber = 3, restaurant = RestaurantStub.getRestaurantForStubs())
        )
        val actual = restaurant.getAvailability(
                date = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(11, 0)),
                peopleNumber = 2
        )

        Assert.assertThat(actual, `is`(Restaurant.AvailabilityType.POSSIBLE))
    }

    @Test
    fun `getAvailability_ NOT_AVAILABLE`() {
        val restaurant = RestaurantStub.getRestaurant()
        val actual = restaurant.getAvailability(
                date = LocalDateTime.of(LocalDate.of(2017, 11, 1), LocalTime.of(11, 0)),
                peopleNumber = 9
        )

        Assert.assertThat(actual, `is`(Restaurant.AvailabilityType.NOT_AVAILABLE))
    }
}