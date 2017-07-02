package pl.denisolek.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByRestaurantIdAndIsVerified(Integer restaurantId, Boolean isVerified);

	List<Reservation> findByReservationBeginGreaterThanEqualAndReservationEndIsLessThanAndRestaurantId(LocalDateTime start, LocalDateTime end, Integer restaurantId);

	List<Reservation> findByDateAndRestaurantIdAndIsVerified(LocalDate date, Integer restaurantId, Boolean isVerified);

	List<Reservation> findByIsVerified(Boolean isVerified);
}
