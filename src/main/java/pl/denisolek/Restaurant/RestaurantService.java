package pl.denisolek.Restaurant;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Restaurant restaurant) {
        return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void increaseCapacity(Restaurant restaurant, Integer spotCount) {
        restaurant.setCapacity(restaurant.getCapacity() + spotCount);
    }

    public void decreaseCapacity(Restaurant restaurant, Integer spotCount) {
        restaurant.setCapacity(restaurant.getCapacity() - spotCount);
    }
}
