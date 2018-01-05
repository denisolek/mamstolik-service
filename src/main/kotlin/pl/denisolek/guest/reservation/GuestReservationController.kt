package pl.denisolek.guest.reservation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
class GuestReservationController(val guestReservationService: GuestReservationService) : GuestReservationApi {

    companion object {
        val API = GuestReservationApi.Companion
    }

    @ApiImplicitParams(
            ApiImplicitParam(name = "reservationId", value = "Reservation Id", paramType = "path", dataType = "int", required = true),
            ApiImplicitParam(name = "code", value = "Code", paramType = "query", dataType = "int", required = true)
    )
    override fun submitCode(@ApiIgnore @PathVariable(API.RESERVATION_ID) reservationId: Reservation,
                            @RequestParam(value = API.CODE, required = true) code: String) =
            guestReservationService.submitCode(reservationId, code)

    override fun addReservation(@RequestBody @Valid createReservationGuestDTO: CreateReservationGuestDTO): Int =
            guestReservationService.addReservation(createReservationGuestDTO)
}