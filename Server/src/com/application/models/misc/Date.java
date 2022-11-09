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
package com.application.models.misc;

import java.io.Serializable;
/**
 * <h1>Date Class</h1>
 * <p>
 * This is the date class
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patternson
 * @version 1.0
 * */
public class Date implements Serializable {
    /**
     *
     */
    public static final String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    /**
     *
     */
    private int day;
    /**
     *
     */
    private int month;
    private int year;

    /**
     * Default constructor for the date class
     */
    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    /**
     *  Primary constructor for the date class
     * @param day
     * @param month
     * @param year
     */
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Copy constructor for the date class
     * @param date
     */
    public Date(Date date) {
        if (date != null) {
            this.day = date.day;
            this.month = date.month;
            this.year = date.year;
        }
    }

    /**
     *  day accessor
     * @return
     */
    public int getDay() {
        return day;
    }

    /**
     * day mutator
     * @param day
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Month accessor
     * @return
     */
    public int getMonth() {
        return month;
    }
    /**
     *  month mutator
     * @return
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * year accessor
     * @return
     */
    public int getYear() {
        return year;
    }
    /**
     *  Year mutator
     * @return
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * convert date to sql date format
     * @return
     */
    public String toSQLDate() {
        return this.year + "-" + this.month + "-" + this.day;
    }

    /**
     * convert date to string
     * @return
     */
    @Override
    public String toString() {
        return this.year + "-" + this.month + "-" + this.day;
    }
}
