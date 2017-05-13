package pl.denisolek.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Reservation.ReservationService;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.User.Requests.UserRegistrationRequest;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserService {

    @Autowired
    ReservationService reservationService;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(UserRegistrationRequest userRegistrationRequest) {
        User newUser = new User();
        Restaurant newRestaurant = new Restaurant();

        validateExistingUser(userRegistrationRequest.getEmail().toLowerCase());
        validateAverageReservationDuration(userRegistrationRequest.getRestaurantAvgReservationTime().getSeconds());
        prepareRestaurant(userRegistrationRequest, newRestaurant);
        prepareUser(userRegistrationRequest, newUser, newRestaurant);

        return userRepository.save(newUser);
    }

    private void validateExistingUser(String email) {
        if (userRepository.findByEmail(email) != null)
            throw new ServiceException(HttpStatus.CONFLICT, "User with this email already exists");
    }

    private void validateAverageReservationDuration(Long duration) {
        if (duration < 900 || duration > 18000)
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Average reservation time can't be less than 15min and higher than 4h");
    }

    private void prepareRestaurant(UserRegistrationRequest userRegistrationRequest, Restaurant newRestaurant) {
        newRestaurant.setName(userRegistrationRequest.getRestaurantName());
        newRestaurant.setCity(userRegistrationRequest.getRestaurantCity());
        newRestaurant.setStreet(userRegistrationRequest.getRestaurantStreet());
        newRestaurant.setLatitude(userRegistrationRequest.getRestaurantLatitude());
        newRestaurant.setLongitude(userRegistrationRequest.getRestaurantLongitude());
        newRestaurant.setAvgReservationTime(userRegistrationRequest.getRestaurantAvgReservationTime());
        newRestaurant.setNip(userRegistrationRequest.getRestaurantNip());
        newRestaurant.setCapacity(userRegistrationRequest.getRestaurantCapacity());
    }

    private void prepareUser(UserRegistrationRequest userRegistrationRequest, User newUser, Restaurant newRestaurant) {
        newUser.setEmail(userRegistrationRequest.getEmail().toLowerCase());
        newUser.setName(userRegistrationRequest.getName());
        newUser.setSurname(userRegistrationRequest.getSurname());
        newUser.setRestaurant(newRestaurant);
    }

    public Restaurant getUserRestaurant(User user) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");

        return user.getRestaurant();
    }

    public List<Reservation> getUserRestaurantReservations(User user, String date) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");
        
        if (date != null){
            LocalDate localDate = LocalDate.parse(date);
            return reservationService.getReservationsAtDate(localDate, user.getRestaurant().getId());
        }
        else {
            return user.getRestaurant().getReservations();
        }
    }

    public User getUser(User user) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");
        return user;
    }
}
