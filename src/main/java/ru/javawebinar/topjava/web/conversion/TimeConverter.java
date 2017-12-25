package ru.javawebinar.topjava.web.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component("timeConverter")
public class TimeConverter implements Converter<String, LocalTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @Override
    public LocalTime convert(String source) {
        try {
            return LocalTime.parse(source, formatter);
        }catch (Exception ignore){
            return null;
        }
    }
}