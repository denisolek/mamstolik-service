package pl.denisolek.guest.restaurant.DTO

import pl.denisolek.core.address.Address
import pl.denisolek.core.restaurant.BusinessHour
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.Restaurant.RestaurantType
import java.time.DayOfWeek
import java.time.LocalDate


data class RestaurantDetailsDTO(
    var id: Int? = null,
    var settings: GuestRestaurantSettingsDTO,
    var name: String,
    var mainImage: String? = null,
    var type: RestaurantType,
    var phoneNumber: String,
    var email: String,
    var description: String?,
    var rate: RestaurantRateDTO,
    var address: Address,
    var menu: List<MenuCategoryDTO>?,
    var tags: List<Any>,
    var businessHours: Map<DayOfWeek, BusinessHour>,
    var openHours: Map<LocalDate, OpenHoursDTO>,
    var comments: List<RestaurantCommentDTO>
) {
    companion object {
        fun fromRestaurant(restaurant: Restaurant): RestaurantDetailsDTO =
            RestaurantDetailsDTO(
                id = restaurant.id,
                settings = setSettings(restaurant),
                name = restaurant.name,
                mainImage = restaurant.mainImage?.uuid,
                type = restaurant.type,
                phoneNumber = restaurant.phoneNumber,
                email = restaurant.email,
                description = setDescription(restaurant),
                rate = setRate(restaurant),
                address = restaurant.address,
                menu = setMenu(restaurant),
                businessHours = restaurant.businessHours,
                tags = listOf(restaurant.cuisineTypes, restaurant.facilities),
                openHours = setOpenHours(restaurant),
                comments = restaurant.comments.map { RestaurantCommentDTO.fromComment(it) }.sortedByDescending { it.dateTime }
            )

        private fun setOpenHours(restaurant: Restaurant): Map<LocalDate, OpenHoursDTO> {
            var firstMonday = LocalDate.now().minusDays(LocalDate.now().dayOfWeek.ordinal.toLong())
            val dates = getDates(firstMonday)

            return dates?.map { date ->
                val specialDate = restaurant.specialDates.find { it.date == date }
                when (specialDate) {
                    null -> Pair(date, OpenHoursDTO.fromBusinessHour(restaurant.getBusinessHoursForDate(date)!!))
                    else -> Pair(date, OpenHoursDTO.fromSpecialDate(specialDate))
                }
            }?.toMap() ?: mapOf()
        }

        private fun getDates(firstMonday: LocalDate): List<LocalDate>? {
            var date = firstMonday
            val dates = mutableListOf<LocalDate>()
            while (date != firstMonday.plusDays(21)) {
                dates.add(date)
                date = date.plusDays(1)
            }
            return dates.toList()
        }

        private fun setSettings(restaurant: Restaurant): GuestRestaurantSettingsDTO {
            return GuestRestaurantSettingsDTO(
                description = restaurant.settings.description,
                localization = restaurant.settings.localization,
                menu = restaurant.settings.menu,
                photos = restaurant.settings.photos
            )
        }

        private fun setDescription(restaurant: Restaurant) =
            if (restaurant.settings.description) restaurant.description else null

        private fun setRate(restaurant: Restaurant): RestaurantRateDTO {
            return RestaurantRateDTO(
                count = restaurant.comments.count(),
                total = restaurant.rate,
                food_rate = restaurant.food_rate,
                price_quality_rate = restaurant.price_quality_rate,
                service_rate = restaurant.service_rate
            )
        }

        private fun setMenu(restaurant: Restaurant): List<MenuCategoryDTO>? {
            return if (restaurant.settings.menu)
                restaurant.menu?.categories?.map { MenuCategoryDTO.fromMenuCategory(it) }?.sortedBy { it.position }
                        ?: listOf()
            else
                null
        }
    }
}