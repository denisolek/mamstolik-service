package pl.denisolek.panel.reservation

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.panel.reservation.DTO.PanelReservationDTO
import pl.denisolek.panel.schema.PanelSchemaApi
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class PanelReservationController(val panelReservationService: PanelReservationService) : PanelReservationApi {
    companion object {
        val API = PanelReservationApi.Companion
    }

    override fun addReservation(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                                @RequestBody @Valid reservationDTO: PanelReservationDTO): List<Reservation> =
            panelReservationService.addReservation(restaurantId, reservationDTO)

}