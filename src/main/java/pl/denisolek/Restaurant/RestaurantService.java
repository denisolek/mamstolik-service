package pl.denisolek.Restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Reservation.ReservationService;
import pl.denisolek.Utils.Tools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
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
        validateSearchParams(city, date, peopleNumber);
        LocalDateTime searchDate = parseSearchDate(date);

        List<Restaurant> cityRestaurants = restaurantRepository.findByCity(city);
        List<Restaurant> availableRestaurants = new ArrayList<>();
        List<Restaurant> openRestaurants = new ArrayList<>();

        getOpenRestaurants(searchDate, cityRestaurants, openRestaurants);
        availableRestaurants.addAll(openRestaurants);
        filterOpenRestaurants(peopleNumber, searchDate, availableRestaurants, openRestaurants);

        return availableRestaurants;
    }

    private LocalDateTime parseSearchDate(String date) {
        LocalDateTime searchDate;
        try {
            searchDate = LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Wrong date format");
        }
        if (LocalDateTime.now(ZoneId.of("Poland")).isAfter(searchDate))
            throw new ServiceException(HttpStatus.BAD_REQUEST, "You cant make reservation in the past");

        return searchDate;
    }

    private void validateSearchParams(String city, String date, Integer peopleNumber) {
        if (city.isEmpty() || date.isEmpty() || peopleNumber == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid request params");
        }

        if (peopleNumber < 1) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "People number must be higher than 0");
        }
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
            checkAvailableSpots(peopleNumber, availableRestaurants, restaurant, duration, checkIntervals, reservationsOverlapping);
        }
    }

    private void checkAvailableSpots(Integer peopleNumber, List<Restaurant> availableRestaurants, Restaurant restaurant, Duration duration, List<LocalDateTime> checkIntervals, List<Reservation> reservationsOverlapping) {
        for (int i = 0; i < checkIntervals.size(); i++) {
            Integer spotsTaken = 0;
            for (int j = 0; j < reservationsOverlapping.size(); j++) {
                if (tools.isBetween(checkIntervals.get(i), reservationsOverlapping.get(j).getReservationBegin(), duration)) {
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

            if (restaurant.isOpen(searchDate.toLocalTime(), searchDateEnd, businessHours.getOpen(), businessHours.getClose()))
                openRestaurants.add(restaurant);
        }
    }
}
