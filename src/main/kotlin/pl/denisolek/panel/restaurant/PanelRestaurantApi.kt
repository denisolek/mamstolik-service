package pl.denisolek.panel.restaurant

import io.swagger.annotations.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.identity.DTO.ChangePasswordDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api("Restaurant controller", tags = arrayOf("Restaurant"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelRestaurantApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"

        const val DETAILS_PATH = "/{$RESTAURANT_ID}/details"
        const val BASE_INFO_PATH = "/{$RESTAURANT_ID}/baseInfo"
        const val PROFILE_PATH = "/{$RESTAURANT_ID}/profile"
        const val RESTAURANT_PASSWORD_PATH = "/{$RESTAURANT_ID}/password"
        const val RESTAURANTS_QUEUE_PATH = "/{$RESTAURANT_ID}/queue"
    }

    @GetMapping(DETAILS_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun getRestaurantDetails(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): PanelRestaurantDetailsDTO

    @PutMapping(BASE_INFO_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun updateBaseInfo(
        @ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid baseInfoDTO: BaseInfoDTO
    ): PanelRestaurantDetailsDTO

    @PutMapping(PROFILE_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun updateProfile(
        @ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid profileDTO: ProfileDTO
    ): PanelRestaurantDetailsDTO

    @PutMapping(RESTAURANT_PASSWORD_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun changeRestaurantPassword(
        @ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid changePasswordDTO: ChangePasswordDTO
    )

    @GetMapping(RESTAURANTS_QUEUE_PATH)
    @PreAuthorize(
        "@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
                "@authorizationService.currentUser.workPlace == #restaurantId"
    )
    fun getRestaurantQueue(@ApiIgnore @PathVariable(RESTAURANT_ID) restaurantId: Restaurant): List<PanelReservationDTO>
}