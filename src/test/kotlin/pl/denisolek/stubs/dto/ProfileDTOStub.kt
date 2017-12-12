package pl.denisolek.stubs.dto

import pl.denisolek.core.restaurant.Restaurant.CuisineType.AMERICAN
import pl.denisolek.core.restaurant.Restaurant.CuisineType.ASIAN
import pl.denisolek.core.restaurant.Restaurant.Facilities.BABY_CORNER
import pl.denisolek.core.restaurant.Restaurant.Facilities.BABY_TOILET
import pl.denisolek.guest.restaurant.DTO.MenuCategoryDTO
import pl.denisolek.guest.restaurant.DTO.MenuItemDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileSettingsDTO

class ProfileDTOStub {
    companion object {
        fun getProfileDTO(): ProfileDTO = ProfileDTO(
                description = "Updated profile",
                cuisineTypes = listOf(ASIAN, AMERICAN),
                facilities = listOf(BABY_CORNER, BABY_TOILET),
                settings = ProfileSettingsDTO(
                        menu = false,
                        description = false,
                        photos = false
                ),
                menu = listOf(
                        MenuCategoryDTO(
                                id = 1,
                                name = "Śniadania",
                                description = "Tylko do godziny 12.00",
                                position = 0,
                                items = listOf(
                                        MenuItemDTO(
                                                id = 1,
                                                name = "Jajecznica z trzech jaj",
                                                description = "Z chlebem wiejskim + boczek",
                                                price = 9f,
                                                position = 0
                                        ),
                                        MenuItemDTO(
                                                id = 2,
                                                name = "Chałka z musem z koziego sera",
                                                description = "Z miodem, orzechami laskowymi oraz rukolą",
                                                price = 11f,
                                                position = 1
                                        ),
                                        MenuItemDTO(
                                                id = 3,
                                                name = "Jajko sadzone w chlebie",
                                                description = "Z boczkiem, pieczarkami, pomidorem, serem oraz szpinakiem",
                                                price = 14f,
                                                position = 2
                                        ),
                                        MenuItemDTO(
                                                id = 4,
                                                name = "Ser koryciński i hummus",
                                                description = "Ze świeżymi warzywami, oliwkami i pieczywem",
                                                price = 12f,
                                                position = 3
                                        )
                                )

                        )
                )

        )
    }
}