package pl.denisolek.panel.restaurant

import org.springframework.stereotype.Service
import pl.denisolek.core.address.City
import pl.denisolek.core.address.CityService
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileDTO

@Service
class PanelRestaurantService(private val restaurantService: RestaurantService,
                             private val cityService: CityService) {
    fun getRestaurantDetails(restaurant: Restaurant): PanelRestaurantDetailsDTO =
            PanelRestaurantDetailsDTO.fromRestaurant(restaurant)

    fun updateBaseInfo(restaurant: Restaurant, baseInfoDTO: BaseInfoDTO): PanelRestaurantDetailsDTO {
        BaseInfoDTO.mapToExistingRestaurant(restaurant, baseInfoDTO)
        restaurant.urlName = restaurantService.generateUrlName(baseInfoDTO.name)
        restaurant.address.city = cityService.findByNameIgnoreCase(baseInfoDTO.address.city) ?: City(name = baseInfoDTO.address.city)
        return PanelRestaurantDetailsDTO.fromRestaurant(restaurantService.save(restaurant))
    }

    fun updateProfile(restaurant: Restaurant, profileDTO: ProfileDTO): PanelRestaurantDetailsDTO {
        ProfileDTO.mapToExistingRestaurant(restaurant, profileDTO)
        return PanelRestaurantDetailsDTO.fromRestaurant(restaurantService.save(restaurant))
    }
}
