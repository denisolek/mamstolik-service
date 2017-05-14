package pl.denisolek.Reservation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Api(value = "reservation", description = "the reservation API", tags = "reservation")
public interface ReservationApi {

	String BASE_PATH = "/restaurants/{restaurantId}/reservations";

	@ApiOperation(value = "Get all reservations", response = Reservation.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
	List<Reservation> getReservations(@PathVariable("restaurantId") Integer restaurantId);

	@ApiOperation(value = "Get reservation by id", response = Reservation.class)
	@ResponseBody
	@RequestMapping(value = "/reservations/{reservationId}", method = RequestMethod.GET)
	Reservation getReservation(@PathVariable("reservationId") Reservation reservation);

	@ApiOperation(value = "Add reservation", response = Reservation.class)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
	Reservation addReservation(@PathVariable("restaurantId") Restaurant restaurant, @RequestBody Reservation reservation);
}
