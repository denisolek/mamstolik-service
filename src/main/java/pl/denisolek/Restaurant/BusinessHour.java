package pl.denisolek.Restaurant;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import pl.denisolek.BaseEntity;
import pl.denisolek.Views;

import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
public class BusinessHour extends BaseEntity {

	@JsonView(Views.RestaurantDetails.class)
	DayOfWeek dayOfWeek;

	@JsonView(Views.RestaurantDetails.class)
	LocalTime open;

	@JsonView(Views.RestaurantDetails.class)
	LocalTime close;
}