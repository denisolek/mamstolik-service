package pl.denisolek.panel.identity.DTO

import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.user.User

data class RestaurantLoginDTO(
        var id: Int,
        var name: String,
        var username: String,
        var avatar: String?,
        var type: Restaurant.RestaurantType,
        var streetName: String,
        var city: String,
        var buildingNumber: String
) {
    companion object {
        fun fromRestaurantUser(user: User, restaurant: Restaurant): RestaurantLoginDTO =
                RestaurantLoginDTO(
                        id = restaurant.id!!,
                        name = restaurant.name,
                        username = user.username!!,
                        avatar = restaurant.mainImage?.uuid,
                        type = restaurant.type,
                        streetName = restaurant.address.streetName,
                        buildingNumber = restaurant.address.buildingNumber,
                        city = restaurant.address.city?.name ?: ""
                )
    }
}