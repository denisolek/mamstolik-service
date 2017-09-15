package pl.denisolek.stubs

import pl.denisolek.core.spot.Spot

class SpotStub {
    companion object {
        fun getSpots(): MutableList<Spot> =
                mutableListOf(
                        Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs()),
                        Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs()),
                        Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs()),
                        Spot(capacity = 5, restaurant = RestaurantStub.getRestaurantForStubs()),
                        Spot(capacity = 2, restaurant = RestaurantStub.getRestaurantForStubs()),
                        Spot(capacity = 2, restaurant = RestaurantStub.getRestaurantForStubs())
                )
    }
}