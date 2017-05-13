package pl.denisolek.Converters;

import javax.persistence.AttributeConverter;
import java.sql.Time;
import java.time.LocalTime;

public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {
    @Override
    public Time convertToDatabaseColumn(LocalTime localTime) {
        return (localTime == null ? null : Time.valueOf(localTime));
    }

    @Override
    public LocalTime convertToEntityAttribute(Time dbData) {
        return (dbData == null ? null : dbData.toLocalTime());
    }
}