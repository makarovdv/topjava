package ru.javawebinar.topjava.web.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

@Component("timeConverter")
public class TimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        if(source == null || source.isEmpty()){
            return null;
        } else {
            return LocalTime.parse(source, ISO_LOCAL_TIME);
        }
    }
}