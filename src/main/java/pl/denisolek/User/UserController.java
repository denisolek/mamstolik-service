package pl.denisolek.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pl.denisolek.Restaurant.Restaurant;

@Controller
public class UserController implements UserApi {

    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @Override
    public Restaurant getUserRestaurant(@PathVariable("userId") User user) {
        return userService.getUserRestaurant(user);
    }
}
