package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.Restaurant

data class RestaurantSearchDTO(
        val id: Int?,
        val name: String,
        val urlName: String,
        val rate: Float,
        val service_rate: Float,
        val food_rate: Float,
        val price_quality_rate: Float,
        val address: Address,
        val cuisineTypes: MutableSet<Restaurant.CuisineType>,
        val facilities: MutableSet<Restaurant.Facilities>) {

    companion object {
        fun fromRestaurant(restaurant: Restaurant): RestaurantSearchDTO =
                RestaurantSearchDTO(
                        id = restaurant.id,
                        name = restaurant.name,
                        urlName = restaurant.urlName,
                        rate = restaurant.rate,
                        service_rate = restaurant.service_rate,
                        food_rate = restaurant.food_rate,
                        price_quality_rate = restaurant.price_quality_rate,
                        address = restaurant.address,
                        cuisineTypes = restaurant.cuisineTypes,
                        facilities = restaurant.facilities
                )
    }
}