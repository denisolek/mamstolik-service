package pl.denisolek.Spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Controller
public class SpotController implements SpotApi {

	@Autowired
	SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}

	@Override
	public List<Spot> getSpots(@PathVariable("restaurantId") Integer restaurantId) {
		return spotService.getSpots(restaurantId);
	}

	@Override
	public Spot getSpot(@PathVariable("spotId") Spot spot) {
		return spotService.getSpot(spot);
	}

	@Override
	public Spot addSpot(@PathVariable("restaurantId") Restaurant restaurant, @RequestBody Spot spot) {
		return spotService.addSpot(restaurant, spot);
	}
}
