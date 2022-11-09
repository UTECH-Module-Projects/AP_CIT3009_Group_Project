/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 *
 */

package com.application.models.converters;

import com.application.models.misc.Date;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
/**
 * <h1>DateConverter Class</h1>
 * <p>
 * This class is intended to translate our custom date from sql date format to our custom date and back again.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 * */
@Converter (autoApply = true)
public class DateConverter implements AttributeConverter<Date, String> {
    /**
     * Converts date to sql Date to be stored in the database
     * @param date
     * @return
     */
    @Override
    public String convertToDatabaseColumn(Date date) {
        if (date == null) return null;
        return date.toSQLDate();
    }

    /**
     *transforms the date obtained from the database into our own data format.
     * @param s
     * @return
     */
    @Override
    public Date convertToEntityAttribute(String s) {
        if (s == null) return null;
        String[] date = s.split("-");

        return new Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
    }
}
