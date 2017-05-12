package pl.denisolek.Reservation;

import org.springframework.stereotype.Component;
import pl.denisolek.Restaurant.Restaurant;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class ReservationService {

	private final ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<Reservation> getReservations(Integer restaurantId) {
		return reservationRepository.findByRestaurantId(restaurantId);
	}

	public Reservation getReservation(Reservation reservation) {
		if (reservation == null)
			throw new EntityNotFoundException();

		return reservation;
	}

	public Reservation addReservation(Restaurant restaurant, Reservation reservation) {
		if (restaurant == null)
			throw new EntityNotFoundException();

		reservation.setRestaurant(restaurant);
		return reservationRepository.save(reservation);
	}
}
