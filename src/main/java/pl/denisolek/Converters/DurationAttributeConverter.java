package pl.denisolek.Converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration == null ? null : duration.getSeconds());
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return (dbData == null ? null : Duration.ofSeconds(dbData));
    }
}