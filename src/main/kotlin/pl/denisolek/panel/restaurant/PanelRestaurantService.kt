package pl.denisolek.panel.restaurant

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.denisolek.Exception.ServiceException
import pl.denisolek.core.address.City
import pl.denisolek.core.address.CityService
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.core.restaurant.RestaurantService
import pl.denisolek.core.user.UserService
import pl.denisolek.panel.identity.DTO.ChangePasswordDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileDTO
import java.time.LocalDateTime

@Service
class PanelRestaurantService(private val restaurantService: RestaurantService,
                             private val cityService: CityService,
                             private val userService: UserService,
                             private val passwordEncoder: PasswordEncoder) {
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

    fun changeRestaurantPassword(restaurant: Restaurant, changePasswordDTO: ChangePasswordDTO) {
        val user = userService.findByRestaurant(restaurant) ?: throw ServiceException(HttpStatus.NOT_FOUND, "Restaurant user not found")
        if (passwordEncoder.matches(changePasswordDTO.oldPassword, user.password))
            user.password = passwordEncoder.encode(changePasswordDTO.newPassword)
        else
            throw ServiceException(HttpStatus.BAD_REQUEST, "Old password doesn't match")
        userService.save(user)
    }

    fun getRestaurantQueue(restaurant: Restaurant): List<PanelReservationDTO> =
            restaurant.reservations.filter {
                it.startDateTime.isAfter(LocalDateTime.now()) &&
                        it.state == Reservation.ReservationState.PENDING
            }.map {
                PanelReservationDTO.fromReservation(it)
            }.sortedBy { it.dateTime }
}
