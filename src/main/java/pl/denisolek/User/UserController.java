package pl.denisolek.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.denisolek.Exception.ServiceException;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.User.Requests.UserRegistrationRequest;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController implements UserApi {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User addUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest, BindingResult result) {
        if (result.hasErrors())
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Invalid request params");

        return userService.addUser(userRegistrationRequest);
    }

    @Override
    public User getUser(@PathVariable("userId") User user) {
        return userService.getUser(user);
    }

    @Override
    public Restaurant getUserRestaurant(@PathVariable("userId") User user) {
        return userService.getUserRestaurant(user);
    }

    @Override
    public List<Reservation> getUserRestaurantReservations(@PathVariable("userId") User user,
                                                           @RequestParam("date") String date) {
        return userService.getUserRestaurantReservations(user, date);
    }
}
