package pl.denisolek.panel.reservation

import io.swagger.annotations.Api
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.restaurant.Restaurant
import pl.denisolek.infrastructure.PANEL_BASE_PATH
import pl.denisolek.panel.reservation.DTO.PanelCreateReservationDTO
import pl.denisolek.panel.reservation.DTO.PanelReservationsDTO
import pl.denisolek.panel.schema.PanelSchemaApi
import springfox.documentation.annotations.ApiIgnore
import java.time.LocalDate
import javax.validation.Valid

@Api("Reservation controller", tags = arrayOf("Reservation"))
@RequestMapping(PANEL_BASE_PATH)
interface PanelReservationApi {
    companion object {
        const val RESTAURANT_ID: String = "restaurantId"
        const val DATE = "date"

        const val RESERVATIONS_PATH = "/{$RESTAURANT_ID}/reservations"
    }

    @PostMapping(RESERVATIONS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun addReservation(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                       @RequestBody @Valid createReservationDTO: PanelCreateReservationDTO): PanelReservationsDTO

    @GetMapping(RESERVATIONS_PATH)
    @PreAuthorize("@authorizationService.currentUser.ownedRestaurants.contains(#restaurantId) || " +
            "@authorizationService.currentUser.workPlace == #restaurantId")
    fun getReservations(@ApiIgnore @PathVariable(PanelSchemaApi.RESTAURANT_ID) restaurantId: Restaurant,
                        @RequestParam(required = true, value = DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate): PanelReservationsDTO
}