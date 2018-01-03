package pl.denisolek.guest.reservation

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO
import javax.validation.Valid

@RestController
class GuestReservationController(val guestReservationService: GuestReservationService): GuestReservationApi {
    override fun addReservation(@RequestBody @Valid createReservationGuestDTO: CreateReservationGuestDTO) =
            guestReservationService.addReservation(createReservationGuestDTO)
}