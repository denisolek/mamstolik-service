package pl.denisolek.Spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.Restaurant.RestaurantService;

import java.util.List;

@Component
public class SpotService {

	private final SpotRepository spotRepository;
	@Autowired
	RestaurantService restaurantService;

	public SpotService(SpotRepository spotRepository) {
		this.spotRepository = spotRepository;
	}

	public List<Spot> getSpots(Integer restaurantId) {
		return spotRepository.findByRestaurantId(restaurantId);
	}

	public Spot getSpot(Spot spot) {
		return spot;
	}

	public Spot addSpot(Restaurant restaurant, Spot spot) {
		if (restaurant == null)
			throw new ServiceException(HttpStatus.NOT_FOUND, "Restaurant not found");

		restaurantService.increaseCapacity(restaurant, spot.getCapacity());
		spot.setRestaurant(restaurant);
		return spotRepository.save(spot);
	}
}
