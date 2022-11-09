package com.application.models.misc;

import java.io.Serializable;

public class Date implements Serializable {
    public static final String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private int day;
    private int month;
    private int year;

    public Date() {
        this.day = 1;
        this.month = 0;
        this.year = 1970;
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(Date date) {
        if (date != null) {
            this.day = date.day;
            this.month = date.month;
            this.year = date.year;
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toSQLDate() {
        return this.year + "-" + this.month + "-" + this.day;
    }

    @Override
    public String toString() {
        return this.year + "-" + this.month + "-" + this.day;
    }
}
