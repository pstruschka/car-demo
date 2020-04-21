package com.peter_struschka.cardemo.query;

import org.springframework.core.convert.converter.Converter;

public class StringStatusTypeConverter implements Converter<String, StatusType> {
    @Override
    public StatusType convert(String string) {
        return StatusType.valueOf(string.toUpperCase());
    }
}

