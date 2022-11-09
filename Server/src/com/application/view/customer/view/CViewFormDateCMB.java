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
package com.application.view.customer.view;

import com.application.view.customer.view.CViewFormPNL;

import javax.swing.*;

public class CViewFormDateCMB {
    public JComboBox<Integer> day;
    public JComboBox<String> month;
    public JComboBox<Integer> year;

    public CViewFormDateCMB() {
        initializeComponents();
    }

    private void initializeComponents() {
        day = new JComboBox<>(CViewFormPNL.days);
        month = new JComboBox<>(CViewFormPNL.months);
        year = new JComboBox<>(CViewFormPNL.years);
    }
}
