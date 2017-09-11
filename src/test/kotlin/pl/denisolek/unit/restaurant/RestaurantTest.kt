package pl.denisolek.unit.restaurant

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
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
}