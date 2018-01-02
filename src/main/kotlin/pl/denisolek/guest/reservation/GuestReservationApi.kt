package pl.denisolek.guest.reservation

import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO
import pl.denisolek.infrastructure.API_BASE_PATH
import javax.validation.Valid

@Api("Reservation controller", tags = arrayOf("Reservation"))
@RequestMapping(API_BASE_PATH)
interface GuestReservationApi {
    companion object {
        const val RESERVATIONS_PATH = "/reservations"
    }

    @PostMapping(RESERVATIONS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    fun addReservation(@RequestBody @Valid createReservationGuestDTO: CreateReservationGuestDTO)
}