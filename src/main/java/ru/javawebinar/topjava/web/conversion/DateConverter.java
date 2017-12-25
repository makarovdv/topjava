package ru.javawebinar.topjava.web.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Component("dateConverter")
public class DateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if(source == null || source.isEmpty()){
            return null;
        } else {
            return LocalDate.parse(source, ISO_LOCAL_DATE);
        }
    }
}
