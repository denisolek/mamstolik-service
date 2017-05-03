package pl.denisolek.Spot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.Restaurant.RestaurantService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class SpotService {

	@Autowired
	RestaurantService restaurantService;

	private final SpotRepository spotRepository;

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
			throw new EntityNotFoundException();

		restaurantService.increaseCapacity(restaurant, spot.getCapacity());
		spot.setRestaurant(restaurant);
		return spotRepository.save(spot);
	}
}
