package pl.denisolek.unit.core.schema

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import pl.denisolek.stubs.FloorStub

@RunWith(MockitoJUnitRunner::class)
class FloorTests {

    @Test
    fun `haveReservationsInFuture_ true`() {
        val floor = FloorStub.getFloorStub()
        floor.restaurant = FloorStub.getRestaurantWithReservations()
        val actual = floor.haveReservationsInFuture()

        Assert.assertTrue(actual)
    }

    @Test
    fun `haveReservationsInFuture_ false`() {
        val floor = FloorStub.getFloorStub()
        val actual = floor.haveReservationsInFuture()

        Assert.assertFalse(actual)
    }
}