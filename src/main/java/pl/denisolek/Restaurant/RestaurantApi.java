package pl.denisolek.Restaurant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "restaurant", description = "the restaurant API", tags = "restaurant")
public interface RestaurantApi {
    String BASE_PATH = "/restaurants";

    @ApiOperation(value = "Get all restaurants", response = Restaurant.class, responseContainer = "List")
    @ResponseBody
    @RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
    List<Restaurant> getRestaurants();

    @ApiOperation(value = "Get restaurant by id", response = Restaurant.class)
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{restaurantId}", method = RequestMethod.GET)
    Restaurant getRestaurant(@PathVariable("restaurantId") Restaurant restaurant);

    @ApiOperation(value = "Add restaurant", response = Restaurant.class)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
    Restaurant addRestaurant(@RequestBody Restaurant restaurant);
}
