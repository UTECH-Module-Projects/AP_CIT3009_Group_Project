/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patterson  2008034
 *
 */

//Package
package com.application.models.misc;

//Imported Libraries

import java.io.Serializable;

/**
 * <h1>Time Class</h1>
 * <p>
 * This Class is designed to store the time record which contains the hours, minutes, and seconds
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public class EntityTime implements Serializable {
    /**
     * The hour section of the time
     */
    private final int hour;

    /**
     * The minute section of the time
     */
    private final int minute;

    /**
     * The second section of the time
     */
    private final int second;

    /**
     * Primary Constructor - Used to store all data for the time
     *
     * @param hour   The hour section of the time
     * @param minute The minute section of the time
     * @param second The second section of the time
     */
    public EntityTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * Converts the Time object to an SQL Time String
     *
     * @return The time object as a sql time string
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", this.hour, this.minute, this.second);
    }
}
