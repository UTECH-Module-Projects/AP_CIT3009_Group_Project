package com.application.view.customer.view;

import javax.swing.*;

public class CViewFormMemBTN {
    public JRadioButton yes;
    public JRadioButton no;
    ButtonGroup grp;

    public CViewFormMemBTN() {
        initializeComponents();
    }

    public void initializeComponents() {
        yes = new JRadioButton("Yes");
        no = new JRadioButton("No");

        grp = new ButtonGroup();
        grp.add(yes);
        grp.add(no);
        no.setSelected(true);
    }
}
