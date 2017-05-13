package pl.denisolek.User;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.User.Requests.UserRegistrationRequest;

import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(UserRegistrationRequest userRegistrationRequest) {
        User newUser = new User();
        Restaurant newRestaurant = new Restaurant();

        prepareRestaurant(userRegistrationRequest, newRestaurant);
        prepareUser(userRegistrationRequest, newUser, newRestaurant);

        return userRepository.save(newUser);
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
        newUser.setEmail(userRegistrationRequest.getEmail());
        newUser.setName(userRegistrationRequest.getName());
        newUser.setSurname(userRegistrationRequest.getSurname());
        newUser.setRestaurant(newRestaurant);
    }

    public Restaurant getUserRestaurant(User user) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");

        return user.getRestaurant();
    }

    public List<Reservation> getUserRestaurantReservations(User user) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");

        return user.getRestaurant().getReservations();
    }

    public User getUser(User user) {
        if (user == null)
            throw new ServiceException(HttpStatus.NOT_FOUND, "User not found");
        return user;
    }
}
