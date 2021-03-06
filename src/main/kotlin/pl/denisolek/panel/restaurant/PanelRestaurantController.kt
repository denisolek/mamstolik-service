package pl.denisolek.panel.restaurant

import io.swagger.annotations.ApiImplicitParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.identity.DTO.ChangePasswordDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.reservation.PanelReservationController
import pl.denisolek.panel.restaurant.DTO.baseInfo.BaseInfoDTO
import pl.denisolek.panel.restaurant.DTO.details.PanelRestaurantDetailsDTO
import pl.denisolek.panel.restaurant.DTO.profile.ProfileDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PanelRestaurantController(val panelRestaurantService: PanelRestaurantService) : PanelRestaurantApi {
    companion object {
        val API = PanelRestaurantApi.Companion
    }

    @ApiImplicitParam(
        name = PanelReservationController.API.RESTAURANT_ID,
        value = "Restaurant Id",
        paramType = "path",
        dataType = "integer"
    )
    override fun getRestaurantDetails(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): PanelRestaurantDetailsDTO =
        panelRestaurantService.getRestaurantDetails(restaurantId)

    @ApiImplicitParam(
        name = PanelReservationController.API.RESTAURANT_ID,
        value = "Restaurant Id",
        paramType = "path",
        dataType = "integer"
    )
    override fun updateBaseInfo(
        @ApiIgnore @PathVariable(PanelRestaurantApi.RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid baseInfoDTO: BaseInfoDTO
    ): PanelRestaurantDetailsDTO =
        panelRestaurantService.updateBaseInfo(restaurantId, baseInfoDTO)

    @ApiImplicitParam(
        name = PanelReservationController.API.RESTAURANT_ID,
        value = "Restaurant Id",
        paramType = "path",
        dataType = "integer"
    )
    override fun updateProfile(
        @ApiIgnore @PathVariable(PanelRestaurantApi.RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid profileDTO: ProfileDTO
    ): PanelRestaurantDetailsDTO =
        panelRestaurantService.updateProfile(restaurantId, profileDTO)

    @ApiImplicitParam(
        name = PanelReservationController.API.RESTAURANT_ID,
        value = "Restaurant Id",
        paramType = "path",
        dataType = "integer"
    )
    override fun changeRestaurantPassword(
        @ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
        @RequestBody @Valid changePasswordDTO: ChangePasswordDTO
    ) =
        panelRestaurantService.changeRestaurantPassword(restaurantId, changePasswordDTO)

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun getRestaurantQueue(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant): List<PanelReservationDTO> =
        panelRestaurantService.getRestaurantQueue(restaurantId)
}