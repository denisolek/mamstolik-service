package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.restaurant.Restaurant

data class UserRestaurantDTO(
        var id: Int,
        var name: String,
        var type: Restaurant.RestaurantType,
        var streetName: String,
        var buildingNumber: String,
        var city: String
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant): UserRestaurantDTO =
                UserRestaurantDTO(
                        id = restaurant.id!!,
                        name = restaurant.name,
                        type = restaurant.type,
                        streetName = restaurant.address?.streetName ?: "",
                        buildingNumber = restaurant.address?.buildingNumber ?: "",
                        city = restaurant.address?.city?.name ?: ""
                )
    }
}