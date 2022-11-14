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

//Package
package com.application.models.converters;

//Imported Libraries

import com.application.models.misc.EntityTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * <h1>Time Converter Class</h1>
 * <p>
 * This Class is designed to convert time between the database and the object
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<EntityTime, String> {

    /**
     * The Time Object Converted to SQL Time String
     *
     * @param entityTime The time object
     * @return The time object converted to sql time string
     */
    @Override
    public String convertToDatabaseColumn(EntityTime entityTime) {
        if (entityTime == null) return null;
        return entityTime.toString();
    }

    /**
     * The SQL Time String converted to Time Object
     *
     * @param s The SQL Time String
     * @return The sql time String converted to time object
     */
    @Override
    public EntityTime convertToEntityAttribute(String s) {
        if (s == null) return null;
        String[] time = s.split(":");

        return new EntityTime(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
    }
}
