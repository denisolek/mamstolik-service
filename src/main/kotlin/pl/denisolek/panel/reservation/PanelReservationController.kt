package pl.denisolek.panel.reservation

import io.swagger.annotations.ApiImplicitParam
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationsDTO
import pl.denisolek.panel.schema.PanelSchemaApi
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@RestController
class PanelReservationController(val panelReservationService: PanelReservationService) : PanelReservationApi {
    companion object {
        val API = PanelReservationApi.Companion
    }

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun addReservation(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                                @RequestBody @Valid createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO =
            panelReservationService.addReservation(restaurantId, createReservationDTO)

    @ApiImplicitParam(name = API.RESTAURANT_ID, value = "Restaurant Id", paramType = "path", dataType = "integer")
    override fun getReservations(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                                 @RequestParam(required = true, value = PanelReservationApi.DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): PanelReservationsDTO =
            panelReservationService.getReservations(restaurantId, date)
}