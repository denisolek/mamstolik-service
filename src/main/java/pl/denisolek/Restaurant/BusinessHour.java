package pl.denisolek.Restaurant;

import lombok.Data;
import pl.denisolek.BaseEntity;

import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
public class BusinessHour extends BaseEntity{

	DayOfWeek dayOfWeek;

	LocalTime open;

	LocalTime close;
}