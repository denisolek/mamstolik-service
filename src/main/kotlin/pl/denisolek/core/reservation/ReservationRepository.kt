package pl.denisolek.core.reservation

import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, Int> {
}