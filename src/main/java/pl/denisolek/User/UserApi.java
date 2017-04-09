package pl.denisolek.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api(value = "User", description = "Operations about user", tags = "user")
public interface UserApi {
    String BASE_PATH = "/users";

    @ApiOperation(value = "Add user", response = User.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = BASE_PATH, method = RequestMethod.POST)
    User addUser(@RequestBody User user);
}
