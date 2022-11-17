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

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class CViewFormMemBTN implements ChangeListener {
    private JRadioButton yes;
    private JRadioButton no;
    private ButtonGroup grp;
    private CViewFormPNL cViewFormPNL;

    /**
     * Primary Constructor
     * Customer Form View for Members
     * @param cViewFormPNL
     */
    public CViewFormMemBTN(CViewFormPNL cViewFormPNL) {
        this.cViewFormPNL = cViewFormPNL;
        initializeComponents();
        setProperties();
    }

    /**
     * initializes components to go form panel
     */
    private void initializeComponents() {
        yes = new JRadioButton("Yes");
        no = new JRadioButton("No");

        grp = new ButtonGroup();
        grp.add(yes);
        grp.add(no);
        no.setSelected(true);
    }

    /**
     * Adding Listeners to the radio buttons
     */
    private void setProperties() {
        yes.addChangeListener(this);
        no.addChangeListener(this);
    }

    /**
     * Listens for state change of the radio buttons
     * state determines visibility of the panel
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (yes.isSelected()) {
            cViewFormPNL.getDomLBL().setVisible(true);
            cViewFormPNL.getDom().getDate().setVisible(true);
            cViewFormPNL.getDomeLBL().setVisible(true);
            cViewFormPNL.getDome().getDate().setVisible(true);
        } else {
            cViewFormPNL.getDomLBL().setVisible(false);
            cViewFormPNL.getDom().getDate().setVisible(false);
            cViewFormPNL.getDomeLBL().setVisible(false);
            cViewFormPNL.getDome().getDate().setVisible(false);
        }
    }
}
