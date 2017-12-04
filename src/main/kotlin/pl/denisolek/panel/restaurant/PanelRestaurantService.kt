package pl.denisolek.panel.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO

@Service
class PanelRestaurantService(private val restaurantService: RestaurantService) {
    fun getRestaurantDetails(restaurant: Restaurant): PanelRestaurantDetailsDTO =
            PanelRestaurantDetailsDTO.fromRestaurant(restaurant)
}
