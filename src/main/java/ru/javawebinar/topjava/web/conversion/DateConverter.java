package ru.javawebinar.topjava.web.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("dateConverter")
public class DateConverter implements Converter<String, LocalDate> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, formatter);
        }catch (Exception e){
            return null;
        }
    }
}
