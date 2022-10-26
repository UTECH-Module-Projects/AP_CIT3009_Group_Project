package com.application.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter (autoApply = true)
public class DateConverter implements AttributeConverter<Date, String> {
    @Override
    public String convertToDatabaseColumn(Date date) {
        if (date == null) return null;
        return date.toSQLDate();
    }

    @Override
    public Date convertToEntityAttribute(String s) {
        if (s == null) return null;
        String[] date = s.split("-");

        return new Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
    }
}
