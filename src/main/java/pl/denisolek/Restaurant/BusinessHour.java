package pl.denisolek.Restaurant;

import lombok.Data;
import pl.denisolek.BaseEntity;

import javax.persistence.Entity;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
@Entity
public class BusinessHour extends BaseEntity{
	DayOfWeek dayOfWeek;
	Time open;
	Time close;
}