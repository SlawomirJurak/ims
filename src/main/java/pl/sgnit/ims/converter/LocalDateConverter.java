package pl.sgnit.ims.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return LocalDate.parse(s);
    }
}
