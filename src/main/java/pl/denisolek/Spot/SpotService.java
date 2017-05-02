package pl.denisolek.Spot;

import org.springframework.stereotype.Component;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Component
public class SpotService {

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
		spot.setRestaurant(restaurant);
		return spotRepository.save(spot);
	}
}
