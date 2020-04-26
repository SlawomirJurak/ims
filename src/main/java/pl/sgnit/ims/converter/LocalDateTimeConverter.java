package pl.sgnit.ims.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(s);
    }
}
