package pl.denisolek.panel.restaurant.DTO.profile

import org.hibernate.validator.constraints.NotBlank
import pl.denisolek.core.menu.item.MenuItem
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.CuisineType
import pl.denisolek.core.restaurant.Restaurant.Facilities
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ProfileDTO(
        var settings: ProfileSettingsDTO,

        @field:Size(max = 1000)
        @field:NotNull
        var description: String,

        var cuisineTypes: List<CuisineType> = listOf(),
        var facilities: List<Facilities> = listOf(),
        var menu: List<MenuCategoryDTO> = listOf()
) {
    companion object {
        fun mapToExistingRestaurant(restaurant: Restaurant, profileDTO: ProfileDTO) {
            restaurant.description = profileDTO.description
            restaurant.settings?.description = profileDTO.settings.description
            restaurant.settings?.photos = profileDTO.settings.photos
            restaurant.settings?.menu = profileDTO.settings.menu
            restaurant.cuisineTypes = profileDTO.cuisineTypes.toMutableSet()
            restaurant.facilities = profileDTO.facilities.toMutableSet()

            updateMenu(restaurant, profileDTO)
        }

        private fun updateMenu(restaurant: Restaurant, profileDTO: ProfileDTO) {
            restaurant.menu?.categories?.removeIf { (id) ->
                !profileDTO.menu.any {
                    it.id == id
                }
            }

            profileDTO.menu.forEach { dtoCategory ->
                restaurant.menu?.categories?.find { it.id == dtoCategory.id }?.let { existingCategory ->
                    existingCategory.name = dtoCategory.name
                    existingCategory.description = dtoCategory.description
                    existingCategory.position = dtoCategory.position
                    existingCategory.items.removeIf { existingItem ->
                        !dtoCategory.items.any { it.id == existingItem.id }
                    }
                    dtoCategory.items.forEach { dtoItem ->
                        existingCategory.items.find { it.id == dtoItem.id }?.let {
                            it.name = dtoItem.name
                            it.description = dtoItem.description
                            it.price = dtoItem.price
                            it.position = dtoItem.position
                        } ?: existingCategory.items.add(MenuItem(
                                name = dtoItem.name,
                                description = dtoItem.description,
                                position = dtoItem.position,
                                price = dtoItem.price,
                                category = existingCategory
                        ))
                    }
                } ?: restaurant.menu?.categories?.add(MenuCategoryDTO.toNewCategory(dtoCategory, restaurant.menu!!))
            }
        }
    }
}