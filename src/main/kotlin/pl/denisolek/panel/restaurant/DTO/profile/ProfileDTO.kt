package pl.denisolek.panel.restaurant.DTO.profile

import pl.denisolek.core.restaurant.Restaurant.CuisineType
import pl.denisolek.core.restaurant.Restaurant.Facilities
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO

data class ProfileDTO(
        var settings: ProfileSettingsDTO,
        var description: String,
        var cuisineTypes: List<CuisineType>,
        var facilities: List<Facilities>,
        var menu: List<MenuCategoryDTO>
)