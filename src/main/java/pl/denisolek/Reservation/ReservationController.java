package pl.denisolek.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Controller
public class ReservationController implements ReservationApi {

	@Autowired
	ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@Override
	public List<Reservation> getReservations(@PathVariable("restaurantId") Integer restaurantId) {
		return reservationService.getReservations(restaurantId);
	}

	@Override
	public Reservation getReservation(@PathVariable("reservationId") Reservation reservation) {
		return reservationService.getReservation(reservation);
	}

	@Override
	public Reservation addReservation(@PathVariable("restaurantId") Restaurant restaurant, @RequestBody Reservation reservation) {
		return reservationService.addReservation(restaurant, reservation);
	}
}
