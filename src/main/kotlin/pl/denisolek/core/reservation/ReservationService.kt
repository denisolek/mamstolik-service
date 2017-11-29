package pl.denisolek.core.reservation

import org.springframework.stereotype.Service
import pl.denisolek.infrastructure.config.security.AuthorizationService

@Service
class ReservationService(private val reservationRepository: ReservationRepository,
                         private val authorizationService: AuthorizationService) {
}