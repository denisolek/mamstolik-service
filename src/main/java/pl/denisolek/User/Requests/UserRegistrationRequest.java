package pl.denisolek.User.Requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
public class UserRegistrationRequest {
    @NotNull
    String email;

    @NotNull
    String name;

    @NotNull
    String surname;

    @NotNull
    String restaurantName;

    @NotNull
    String restaurantCity;

    @NotNull
    String restaurantStreet;

    @NotNull
    Float restaurantLatitude;

    @NotNull
    Float restaurantLongitude;

    @NotNull
    Duration restaurantAvgReservationTime;

    @NotNull
    String restaurantNip;

    @NotNull
    Integer restaurantCapacity;
}
