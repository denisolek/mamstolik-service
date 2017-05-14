package pl.denisolek.User;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvailableCapacityAtDate {
    LocalDateTime date;
    Integer capacity;

    public AvailableCapacityAtDate(LocalDateTime date, Integer capacity) {
        this.date = date;
        this.capacity = capacity;
    }
}
