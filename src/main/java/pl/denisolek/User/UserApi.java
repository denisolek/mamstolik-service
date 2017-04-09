package pl.denisolek.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


public interface UserApi {
    String BASE_PATH = "/users";

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
    User addUser(@RequestBody User user);
}
