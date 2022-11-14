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

import com.application.models.misc.EntityDate;
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
public class DateConverter implements AttributeConverter<EntityDate, String> {
    /**
     * The Date Object Converted to SQL Date String
     *
     * @param entityDate The Date object
     * @return The date object converted to sql date string
     */
    @Override
    public String convertToDatabaseColumn(EntityDate entityDate) {
        if (entityDate == null) return null;
        return entityDate.toString();
    }

    /**
     * The SQL Date String converted to Date Object
     *
     * @param s The SQL Date String
     * @return The sql date String converted to date object
     */
    @Override
    public EntityDate convertToEntityAttribute(String s) {
        if (s == null) return null;
        return new EntityDate(s.split("-"));
    }
}
