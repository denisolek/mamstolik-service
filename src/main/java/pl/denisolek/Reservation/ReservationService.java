package pl.denisolek.Reservation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Restaurant.Restaurant;

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
			throw new ServiceException(HttpStatus.NOT_FOUND, "Reservation not found");

		return reservation;
	}

	public Reservation addReservation(Restaurant restaurant, Reservation reservation) {
		if (restaurant == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Restaurant not found");

		reservation.setRestaurant(restaurant);
		return reservationRepository.save(reservation);
	}
}
