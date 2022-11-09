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
