package ru.javawebinar.topjava.web.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("conversionService")
public class ApplicationConversionService extends DefaultFormattingConversionService {

    @Autowired
    public ApplicationConversionService(List<Converter> converters){
        super();
        converters.forEach(this::addConverter);
    }
}