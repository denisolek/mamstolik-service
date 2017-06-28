package pl.denisolek.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RestaurantController implements RestaurantApi {

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@Override
	public List<Restaurant> getRestaurants() {
		return restaurantService.getRestaurants();
	}

	@Override
	public Restaurant getRestaurant(@PathVariable("restaurantId") Restaurant restaurant) {
		return restaurantService.getRestaurant(restaurant);
	}

	@Override
	public Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantService.addRestaurant(restaurant);
	}

	@Override
	public List<Restaurant> searchRestaurants(@RequestParam(value = "city", required = false) String city,
	                                          @RequestParam(value = "date", required = false) String date,
	                                          @RequestParam(value = "peopleNumber", required = false) Integer peopleNumber) {
		return restaurantService.searchRestaurants(city, date, peopleNumber);
	}

	@Override
	public Restaurant changeActiveState(@PathVariable("restaurantId") Restaurant restaurant) {
		return restaurantService.changeActiveState(restaurant);
	}
}
