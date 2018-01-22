package pl.denisolek.guest.reservation

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pl.denisolek.core.reservation.Reservation
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO
import pl.denisolek.infrastructure.API_BASE_PATH
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api("Reservation controller", tags = arrayOf("Reservation"))
@RequestMapping(API_BASE_PATH)
interface GuestReservationApi {
    companion object {
        const val RESERVATION_ID = "reservationId"
        const val CODE = "code"

        const val RESERVATIONS_PATH = "/reservations"
        const val RESERVATION_ID_CODE_APATH = "$RESERVATIONS_PATH/{$RESERVATION_ID}/code"
    }

    @PostMapping(RESERVATIONS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    fun addReservation(@RequestBody @Valid createReservationGuestDTO: CreateReservationGuestDTO): Int

    @PostMapping(RESERVATION_ID_CODE_APATH)
    fun submitCode(
        @ApiIgnore @PathVariable(RESERVATION_ID) reservationId: Reservation,
        @RequestParam(value = CODE, required = true) code: String
    )
}