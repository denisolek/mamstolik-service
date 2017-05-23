package pl.denisolek.User.Requests;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;

@Data
public class UserRegistrationRequest {

	@NotNull
	@Email
	String email;

	@NotNull
	@Size(min = 3, max = 50)
	String name;

	@NotNull
	@Size(min = 3, max = 50)
	String surname;

	@NotNull
	@Size(min = 3, max = 200)
	String restaurantName;

	@NotNull
	@Size(min = 3, max = 100)
	String restaurantCity;

	@NotNull
	@Size(min = 3, max = 150)
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
	@Min(value = 1)
	@Max(value = 1000)
	Integer restaurantCapacity;
}
