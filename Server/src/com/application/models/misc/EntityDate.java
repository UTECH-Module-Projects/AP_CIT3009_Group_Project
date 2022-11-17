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
package com.application.models.misc;

//Imported Libraries

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Date Class</h1>
 * <p>
 * This Class is designed to store the date record which contains the day, month, and year.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Getter
@Setter
public class EntityDate implements Serializable {
    /**
     * Used to store the full name of the months of the year
     */
    public static final String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static final float daysInYear = 365.24f;

    public static final String sqlStr = "yyyy-MM-dd";
    public static final String sqlDel = "-";

    public static final SimpleDateFormat sqlFormat = new SimpleDateFormat(sqlStr, Locale.ENGLISH);

    private int year;
    private int month;
    private int day;

    /**
     * Default constructor
     */
    public EntityDate() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
    }

    /**
     * Primary constructor
     *
     * @param day   The day of the month
     * @param month The month of the year
     * @param year  The year of the date
     */
    public EntityDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Copy constructor (Java Utility Variant)
     *
     * @param utilDate The java utility date
     */
    public EntityDate(Date utilDate) {
        EntityDate date = toEntityDate(utilDate);
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
    }

    /**
     * Copy constructor (Array String Variant)
     *
     * @param fields The array of string fields
     */
    public EntityDate(String[] fields) {
        this.year = Integer.parseInt(fields[0]);
        this.month = Integer.parseInt(fields[1]);
        this.day = Integer.parseInt(fields[2]);
    }

    public static EntityDate today() {
        return toEntityDate(new Date());
    }

    public static String format(Date date) {
        return sqlFormat.format(date);
    }

    public static String[] split(Date date) {
        return format(date).split(sqlDel);
    }

    public static EntityDate toEntityDate(Date date) {
        return new EntityDate(split(date));
    }

    public static Date toUtilDate(EntityDate date) {
        return toUtilDate(date.toString());

    }

    public static Date toUtilDate(String date) {
        boolean flag = true;
        Date utilDate = null;
        do {
            try {
                utilDate = sqlFormat.parse(date);
                flag = false;
            } catch (Exception ignored) {}
        } while (flag);

        return utilDate;
    }

    /**
     * Compares the current date with another date.
     * date > date2: 1
     * date < date2: -1
     * date = date2: 0
     *
     * @param entityDate2 The second date to compare
     * @return The result of the comparison
     */
    public int compare(EntityDate entityDate2) {
        if (this.year != entityDate2.getYear())
            return Integer.compare(this.year, entityDate2.getYear());
        else if (this.month != entityDate2.getMonth())
            return Integer.compare(this.month, entityDate2.getMonth());
        else
            return Integer.compare(this.day, entityDate2.getDay());
    }

    public long dateDiff(EntityDate entityDate2) {
        java.util.Date dateBef = this.toUtilDate();
        java.util.Date dateAft = entityDate2.toUtilDate();
        return TimeUnit.DAYS.convert(Math.abs(dateAft.getTime() - dateBef.getTime()), TimeUnit.MILLISECONDS);
    }

    public Date toUtilDate() {
        return toUtilDate(this);
    }

    /**
     * Converts the Date object to an SQL Date String
     *
     * @return The date object as a sql date string
     */
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", this.year, this.month, this.day);
    }
}
