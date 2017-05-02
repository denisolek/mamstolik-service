package pl.denisolek.Spot;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Api(value = "spot", description = "the spot API", tags = "spot")
public interface SpotApi {

	String BASE_PATH = "/restaurants/{restaurantId}/spots";

	@ApiOperation(value = "Get all spots", response = Spot.class, responseContainer = "List")
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
	List<Spot> getSpots(@PathVariable("restaurantId") Integer restaurantId);

	@ApiOperation(value = "Get spot by id", response = Spot.class)
	@ResponseBody
	@RequestMapping(value = "/spots/{spotId}", method = RequestMethod.GET)
	Spot getSpot(@PathVariable("spotId") Spot spot);

	@ApiOperation(value = "Add spot", response = Spot.class)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
	Spot addSpot(@PathVariable("restaurantId") Restaurant restaurant, @RequestBody Spot spot);
}
