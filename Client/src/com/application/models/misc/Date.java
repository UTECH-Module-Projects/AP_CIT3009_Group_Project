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

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class Date implements Serializable {
    /**
     * Used to store the full name of the months of the year
     */
    public static final String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static final String sqlFormat = "yyyy-MM-dd";

    private int year;
    private int month;
    private int day;

    /**
     * Default Constructor
     */
    public Date() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
    }

    /**
     * Primary constructor for the date class
     *
     * @param day   The day of the month
     * @param month The month of the year
     * @param year  The year of the date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     *
     * @param utilDate The Java Utility Data
     */
    public Date(java.util.Date utilDate) {
        Date date = utilDateToDate(utilDate);
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
    }

    /**
     * Constructor used to initialize a date using string fields
     *
     * @param fields The array of string fields to create the new date
     */
    public Date(String[] fields) {
        this.year = Integer.parseInt(fields[0]);
        this.month = Integer.parseInt(fields[1]);
        this.day = Integer.parseInt(fields[2]);
    }

    /**
     * Compares the current date with another date.
     * date > date2: 1
     * date < date2: -1
     * date = date2: 0
     *
     * @param date2 The second date to compare
     * @return The result of the comparison
     */
    public int compare(Date date2) {
        if (this.year != date2.getYear())
            return Integer.compare(this.year, date2.getYear());
        else if (this.month != date2.getMonth())
            return Integer.compare(this.month, date2.getMonth());
        else
            return Integer.compare(this.day, date2.getDay());
    }

    public long dateDiff(Date date2) throws ParseException {
        java.util.Date dateBef = this.toUtilDate();
        java.util.Date dateAft = date2.toUtilDate();
        return TimeUnit.DAYS.convert(Math.abs(dateAft.getTime() - dateBef.getTime()), TimeUnit.MILLISECONDS);
    }


    /**
     * Converts a SQL Date String to a Date Object
     *
     * @param sqlDate The SQL Date String
     * @return The sql date string as a date object
     */
    public static Date sqlToDate(String sqlDate) {
        if (sqlDate == null || sqlDate.equals("")) return null;
        return new Date(sqlDate.split("-"));
    }

    public static Date utilDateToDate(java.util.Date date) {
        return new Date(new SimpleDateFormat(sqlFormat).format(date).split("-"));
    }

    public static java.util.Date toUtilDate(String sqlStr) throws ParseException {
        return new SimpleDateFormat(sqlFormat).parse(sqlStr);
    }

    public java.util.Date toUtilDate() throws ParseException {
        return Date.toUtilDate(this.toString());
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
