package pl.denisolek.panel.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO

@Service
class PanelRestaurantService(private val restaurantService: RestaurantService) {
    fun getRestaurantDetails(restaurant: Restaurant): PanelRestaurantDetailsDTO =
            PanelRestaurantDetailsDTO.fromRestaurant(restaurant)

    fun updateBaseInfo(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): PanelRestaurantDetailsDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
