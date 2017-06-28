package pl.denisolek.Reservation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.Views;

import java.util.List;

@Api(value = "reservation", description = "the reservation API", tags = "reservation")
public interface ReservationApi {

	String RESTAURANT_RESERVATIONS_PATH = "/restaurants/{restaurantId}/reservations";
	String ALL_RESERVATIONS_PATH = "/reservations";
	String SINGLE_RESERVATION_PATH =  "/reservations/{reservationId}";

	@JsonView(Views.ReservationDetails.class)
	@ApiOperation(value = "Get all reservations", response = Reservation.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = ALL_RESERVATIONS_PATH, method = RequestMethod.GET)
	List<Reservation> getReservations();

	@JsonView(Views.Reservation.class)
	@ApiOperation(value = "Get all restaurant reservations", response = Reservation.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = RESTAURANT_RESERVATIONS_PATH, method = RequestMethod.GET)
	List<Reservation> getRestaurantReservations(@PathVariable("restaurantId") Integer restaurantId);

	@JsonView(Views.ReservationDetails.class)
	@ApiOperation(value = "Get reservation by id", response = Reservation.class)
	@ResponseBody
	@RequestMapping(value = SINGLE_RESERVATION_PATH, method = RequestMethod.GET)
	Reservation getReservation(@PathVariable("reservationId") Reservation reservation);

	@ApiOperation(value = "Add reservation", response = Reservation.class)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(value = RESTAURANT_RESERVATIONS_PATH, method = RequestMethod.POST)
	Reservation addReservation(@PathVariable("restaurantId") Restaurant restaurant, @RequestBody Reservation reservation);

	@ApiOperation(value = "Remove reservation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = SINGLE_RESERVATION_PATH, method = RequestMethod.DELETE)
	void removeReservation(@PathVariable("reservationId") Reservation reservation);
}
