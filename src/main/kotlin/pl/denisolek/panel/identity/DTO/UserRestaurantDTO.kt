package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.restaurant.Restaurant

data class UserRestaurantDTO(
        var id: Int,
        var name: String,
        var address: String
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant): UserRestaurantDTO =
                UserRestaurantDTO(
                        id = restaurant.id!!,
                        name = restaurant.name,
                        address = restaurant.address?.getAddressString()!!
                )
    }
}