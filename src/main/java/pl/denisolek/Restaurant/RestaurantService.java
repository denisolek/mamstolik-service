package pl.denisolek.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.denisolek.Reservation.ReservationService;
import pl.denisolek.Utils.TimeOperations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantService {

    @Autowired
    ReservationService reservationService;

    @Autowired
    TimeOperations timeOperations;

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

    public List<Restaurant> searchRestaurants(String city, String date, Integer peopleNumber) {
        List<Restaurant> availableRestaurants = new ArrayList<>();
        LocalDateTime searchDate = LocalDateTime.parse(date);
        List<Restaurant> cityRestaurants = restaurantRepository.findByCity(city);
        List<Restaurant> openRestaurants = new ArrayList<>();

        getOpenRestaurants(searchDate, cityRestaurants, openRestaurants);

        return availableRestaurants;
    }

    private void getOpenRestaurants(LocalDateTime searchDate, List<Restaurant> cityRestaurants, List<Restaurant> openRestaurants) {
        for (int i = 0; i < cityRestaurants.size(); i++) {
            Restaurant restaurant = cityRestaurants.get(i);
            LocalTime searchDateEnd = searchDate.plus(restaurant.getAvgReservationTime()).toLocalTime();
            BusinessHour businessHours = reservationService.getDateBusinessHour(restaurant.getBusinessHours(), searchDate.toLocalDate());

            if (timeOperations.isContaining(searchDate.toLocalTime(), searchDateEnd, businessHours.getOpen(), businessHours.getClose()))
                openRestaurants.add(restaurant);
        }
    }
}
