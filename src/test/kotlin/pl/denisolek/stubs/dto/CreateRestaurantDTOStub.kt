package pl.denisolek.stubs.dto

import pl.denisolek.core.restaurant.Restaurant.RestaurantType.RESTAURANT
import pl.denisolek.panel.identity.DTO.CreateRestaurantDTO

class CreateRestaurantDTOStub {
    companion object {
        fun getCreateRestaurantDTOStub() =
                CreateRestaurantDTO(
                        name = "Stub Name",
                        email = "stub@test.pl",
                        type = RESTAURANT,
                        phoneNumber = "111222333",
                        password = "Test12345"
                )
    }
}