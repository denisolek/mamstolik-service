package pl.denisolek.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.denisolek.Reservation.Reservation;
import pl.denisolek.Restaurant.Restaurant;
import pl.denisolek.User.Requests.UserRegistrationRequest;

import javax.validation.Valid;
import java.util.List;


@Api(value = "User", description = "Operations about user", tags = "user")
public interface UserApi {
    String BASE_PATH = "/users";

    @ApiOperation(value = "Add user with restaurant", response = User.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
    User addUser(@RequestBody @Valid UserRegistrationRequest UserRegistrationRequest, BindingResult result);

    @ApiOperation(value = "Get user", response = User.class)
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{userId}", method = RequestMethod.GET)
    User getUser(@PathVariable("userId") User user);

    @ApiOperation(value = "Get user restaurant", response = Restaurant.class)
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{userId}/restaurants", method = RequestMethod.GET)
    Restaurant getUserRestaurant(@PathVariable("userId") User user);

    @ApiOperation(value = "Get user reservations", response = Reservation.class, responseContainer = "List")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{userId}/reservations", method = RequestMethod.GET)
    List<Reservation> getUserRestaurantReservations(@PathVariable("userId") User user,
                                                    @RequestParam("date") String date);
}
