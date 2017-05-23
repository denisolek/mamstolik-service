package pl.denisolek.Restaurant;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.denisolek.Views;

import java.util.List;

@Api(value = "restaurant", description = "the restaurant API", tags = "restaurant")
public interface RestaurantApi {
	String BASE_PATH = "/restaurants";

	@ApiOperation(value = "Get all restaurants", response = Restaurant.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
	List<Restaurant> getRestaurants();

	@JsonView(Views.RestaurantDetails.class)
	@ApiOperation(value = "Get restaurant by id", response = Restaurant.class)
	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/{restaurantId}", method = RequestMethod.GET)
	Restaurant getRestaurant(@PathVariable("restaurantId") Restaurant restaurant);

	@ApiOperation(value = "Add restaurant", response = Restaurant.class)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
	Restaurant addRestaurant(@RequestBody Restaurant restaurant);

	@JsonView(Views.Restaurant.class)
	@ApiOperation(value = "Get restaurant by reservations", response = Restaurant.class)
	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/search", method = RequestMethod.GET)
	List<Restaurant> searchRestaurants(@RequestParam(value = "city", required = false) String city,
	                                   @RequestParam(value = "date", required = false) String date,
	                                   @RequestParam(value = "peopleNumber", required = false) Integer peopleNumber);
}
