package pl.denisolek.stubs

import pl.denisolek.core.spot.Spot

class SpotStub {
    companion object {
        fun getSpots(): MutableList<Spot> =
                mutableListOf(
                        Spot(5, RestaurantStub.getRestaurantForStubs()),
                        Spot(5, RestaurantStub.getRestaurantForStubs()),
                        Spot(5, RestaurantStub.getRestaurantForStubs()),
                        Spot(5, RestaurantStub.getRestaurantForStubs()),
                        Spot(2, RestaurantStub.getRestaurantForStubs()),
                        Spot(2, RestaurantStub.getRestaurantForStubs())
                )
    }
}