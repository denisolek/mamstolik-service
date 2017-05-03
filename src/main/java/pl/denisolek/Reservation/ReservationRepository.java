package pl.denisolek.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	List<Reservation> findByRestaurantId(Integer restaurantId);
}
