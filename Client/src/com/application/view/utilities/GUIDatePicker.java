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
package com.application.view.utilities;

import com.application.models.misc.EntityDate;
import lombok.Getter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

@Getter
public class GUIDatePicker {
    private UtilDateModel mdl;
    private Properties prop;
    private JDatePanelImpl dPNL;
    private JDatePickerImpl date;

    /**
     * Default constructor
     */
    public GUIDatePicker() {
        initializeComponents();
    }

    /**
     * Creates the GUI date picker calendar
     */
    private void initializeComponents() {
        mdl = new UtilDateModel();
        prop = new Properties();
        prop.put("text.day", "Day");
        prop.put("text.today", "Today");
        prop.put("text.month", "Month");
        prop.put("text.year", "Year");
        mdl.setSelected(true);

        dPNL = new JDatePanelImpl(mdl, prop);
        date = new JDatePickerImpl(dPNL, new JFormattedTextField.AbstractFormatter() {
            private final String pattern = "yyyy-MM-dd";
            private final SimpleDateFormat format = new SimpleDateFormat(pattern);

            @Override
            public Object stringToValue(String text) throws ParseException {
                return format.parseObject(text);
            }

            @Override
            public String valueToString(Object value) {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return format.format(cal.getTime());
                }

                return "";
            }
        });
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", mdl.getYear(), mdl.getMonth()+1, mdl.getDay());
    }

    public EntityDate toDate() {
        return new EntityDate(mdl.getYear(), mdl.getMonth()+1, mdl.getDay());
    }

    public void setVisible(boolean isVisible) {
        date.setVisible(isVisible);
    }

    public void setDate(int year, int month, int day) {
        mdl.setDate(year, month-1, day);
    }

    public void setDate(String[] fields) {
        mdl.setDate(Integer.parseInt(fields[0]), Integer.parseInt(fields[1])-1, Integer.parseInt(fields[2]));
    }

    public void setDate(EntityDate date) {
        mdl.setDate(date.getYear(), date.getMonth()-1, date.getDay());
    }
}
