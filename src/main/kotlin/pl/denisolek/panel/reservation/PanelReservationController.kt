package pl.denisolek.panel.reservation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationsDTO
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@RestController
class PanelReservationController(val panelReservationService: PanelReservationService) : PanelReservationApi {
    companion object {
        val API = PanelReservationApi.Companion
    }

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun addReservation(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                                @RequestBody @Valid createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO =
            panelReservationService.addReservation(restaurantId, createReservationDTO)

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun getReservations(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                                 @RequestParam(required = true, value = PanelReservationApi.DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): PanelReservationsDTO =
            panelReservationService.getReservations(restaurantId, date)

    @ApiImplicitParams(
            ApiImplicitParam(name = "restaurantId", value = "Restaurant Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = "reservationId", value = "Reservation Id", paramType = "path", dataType = "int", required = true)
    )
    override fun editReservation(@ApiIgnore @PathVariable(PanelReservationApi.RESTAURANT_ID) restaurantId: Restaurant,
                                 @ApiIgnore @PathVariable(PanelReservationApi.RESERVATION_ID) reservationId: Reservation,
                                 @RequestBody @Valid createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO =
            panelReservationService.editReservation(restaurantId, reservationId, createReservationDTO)

    @ApiImplicitParams(
            ApiImplicitParam(name = "restaurantId", value = "Restaurant Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = "reservationId", value = "Reservation Id", paramType = "path", dataType = "int", required = true)
    )
    override fun cancelReservation(@ApiIgnore @PathVariable(API.RESTAURANT_ID) restaurantId: Restaurant,
                                   @ApiIgnore @PathVariable(API.RESERVATION_ID) reservationId: Reservation): PanelReservationsDTO =
            panelReservationService.cancelReservation(restaurantId, reservationId)
}