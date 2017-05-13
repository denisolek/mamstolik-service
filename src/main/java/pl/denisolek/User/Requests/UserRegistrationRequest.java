package pl.denisolek.User.Requests;

import lombok.Data;

import java.time.Duration;

@Data
public class UserRegistrationRequest {
    String email;

    String name;

    String surname;

    String restaurantName;

    String restaurantCity;

    String restaurantStreet;

    Integer restaurantLatitude;

    Integer restaurantLongitude;

    Duration restaurantAvgReservationTime;

    Integer restaurantNip;

    Integer restaurantCapacity;
}
