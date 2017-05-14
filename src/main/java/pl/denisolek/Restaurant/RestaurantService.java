package pl.denisolek.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Reservation.ReservationService;
import pl.denisolek.Utils.Tools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantService {

    @Autowired
    Tools tools;

    @Autowired
    ReservationService reservationService;

    private final RestaurantRepository restaurantRepository;
    private final Integer CHECKING_INTERVAL = 15;

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

    public List<Restaurant> searchRestaurants(String city, String date, Integer peopleNumber) {
        LocalDateTime searchDate = LocalDateTime.parse(date);
        List<Restaurant> cityRestaurants = restaurantRepository.findByCity(city);
        List<Restaurant> availableRestaurants = new ArrayList<>();
        List<Restaurant> openRestaurants = new ArrayList<>();

        getOpenRestaurants(searchDate, cityRestaurants, openRestaurants);
        availableRestaurants.addAll(openRestaurants);
        filterOpenRestaurants(peopleNumber, searchDate, availableRestaurants, openRestaurants);

        return availableRestaurants;
    }

    private void filterOpenRestaurants(Integer peopleNumber, LocalDateTime searchDate, List<Restaurant> availableRestaurants, List<Restaurant> openRestaurants) {
        for (int i = 0; i < openRestaurants.size(); i++) {
            Restaurant restaurant = openRestaurants.get(i);
            Duration duration = restaurant.getAvgReservationTime();
            List<LocalDateTime> checkIntervals = new ArrayList<>();
            LocalDateTime startSearchDate = searchDate.minus(restaurant.getAvgReservationTime());
            LocalDateTime endSearchDate = searchDate.plus(restaurant.getAvgReservationTime().multipliedBy(2));
            List<Reservation> reservationsOverlapping = reservationService.getReservationsBetween(startSearchDate, endSearchDate, restaurant.getId());

            tools.getDatesToCheck(checkIntervals, searchDate, duration, CHECKING_INTERVAL);
            checkAvailableSpots(peopleNumber, availableRestaurants, checkIntervals.get(i), restaurant, duration, checkIntervals, reservationsOverlapping);
        }
    }

    private void checkAvailableSpots(Integer peopleNumber, List<Restaurant> availableRestaurants, LocalDateTime checkingInterval, Restaurant restaurant, Duration duration, List<LocalDateTime> checkIntervals, List<Reservation> reservationsOverlapping) {
        for (int i = 0; i < checkIntervals.size(); i++) {
            Integer spotsTaken = 0;
            for (int j = 0; j < reservationsOverlapping.size(); j++) {
                if (tools.isBetween(checkingInterval, reservationsOverlapping.get(j).getReservationBegin(), duration)) {
                    spotsTaken += reservationsOverlapping.get(j).getPeopleNumber();
                }
            }
            if (spotsTaken + peopleNumber > restaurant.getCapacity()) {
                availableRestaurants.remove(restaurant);
                break;
            }
        }
    }

    private void getOpenRestaurants(LocalDateTime searchDate, List<Restaurant> cityRestaurants, List<Restaurant> openRestaurants) {
        for (int i = 0; i < cityRestaurants.size(); i++) {
            Restaurant restaurant = cityRestaurants.get(i);
            LocalTime searchDateEnd = searchDate.plus(restaurant.getAvgReservationTime()).toLocalTime();
            BusinessHour businessHours = tools.getDateBusinessHour(restaurant.getBusinessHours(), searchDate.toLocalDate());

            if (tools.isContaining(searchDate.toLocalTime(), searchDateEnd, businessHours.getOpen(), businessHours.getClose()))
                openRestaurants.add(restaurant);
        }
    }
}
