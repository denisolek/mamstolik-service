package pl.denisolek.core.reservation

import org.springframework.stereotype.Service

@Service
class ReservationService(private val reservationRepository: ReservationRepository) {
    fun save(reservation: Reservation) =
            reservationRepository.save(reservation)
}