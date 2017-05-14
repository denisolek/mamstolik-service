package pl.denisolek.Utils;

import org.springframework.stereotype.Component;
import pl.denisolek.Restaurant.BusinessHour;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Component
public class Tools {

    public BusinessHour getDateBusinessHour(Set<BusinessHour> businessHours, LocalDate date) {
        for (BusinessHour businessHour: businessHours) {
            if (businessHour.getDayOfWeek() == date.getDayOfWeek())
                return businessHour;
        }
        return null;
    }

    public boolean isBetween(LocalDateTime checkingInterval, LocalDateTime interval, Duration duration) {
        LocalDateTime intervalEnd = interval.plus(duration);
        return ((checkingInterval.isAfter(interval) || checkingInterval.isEqual(interval)) && checkingInterval.isBefore(intervalEnd));
    }

    public boolean isContaining(LocalTime intervalStart, LocalTime intervalEnd, LocalTime businessHourStart, LocalTime businessHourEnd) {
        return (intervalStart.isAfter(businessHourStart) && intervalEnd.isBefore(businessHourEnd));
    }
}
