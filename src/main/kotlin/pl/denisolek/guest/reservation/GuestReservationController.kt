package pl.denisolek.guest.reservation

import org.springframework.web.bind.annotation.RestController
import pl.denisolek.guest.reservation.DTO.CreateReservationGuestDTO

@RestController
class GuestReservationController(val guestReservationService: GuestReservationService): GuestReservationApi {
    override fun addReservation(createReservationGuestDTO: CreateReservationGuestDTO) =
            guestReservationService.addReservation(createReservationGuestDTO)
}