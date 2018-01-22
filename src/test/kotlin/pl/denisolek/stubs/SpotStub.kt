package pl.denisolek.stubs

import pl.denisolek.core.spot.Spot

class SpotStub {
    companion object {
        fun getSpots(): MutableList<Spot> =
            mutableListOf(
                Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs(), number = 1),
                Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs(), number = 2),
                Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs(), number = 3),
                Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs(), number = 4),
                Spot(capacity = 2, restaurant = RestaurantStub.getRestaurantForStubs(), number = 5),
                Spot(capacity = 2, restaurant = RestaurantStub.getRestaurantForStubs(), number = 6)
            )
    }
}