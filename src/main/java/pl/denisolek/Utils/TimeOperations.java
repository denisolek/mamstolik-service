package pl.denisolek.Utils;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeOperations {
    public boolean isBetween(LocalDateTime checkingInterval, LocalDateTime interval, Duration duration) {
        LocalDateTime intervalEnd = interval.plus(duration);
        return ((checkingInterval.isAfter(interval) || checkingInterval.isEqual(interval)) && checkingInterval.isBefore(intervalEnd));
    }

    public boolean isContaining(LocalTime intervalStart, LocalTime intervalEnd, LocalTime businessHourStart, LocalTime businessHourEnd) {
        return (intervalStart.isAfter(businessHourStart) && intervalEnd.isBefore(businessHourEnd));
    }
}
