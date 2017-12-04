package pl.denisolek.panel.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.RestaurantService

@Service
class PanelRestaurantService(private val restaurantService: RestaurantService) {
}