package pl.denisolek.User;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Restaurant.Restaurant;

import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
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
}
