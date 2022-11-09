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
package com.application.view;

import com.application.view.customer.CViewPNL;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class CPNL {
    private final ServerApp serverApp;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private CViewPNL view;

    public CPNL(ServerApp serverApp) {
        this.serverApp = serverApp;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill"));
        tPNE = new JTabbedPane();

        view = new CViewPNL(this);
    }

    private void addComponents() {
        tPNE.add("View Customer", view.getPnl());
//        tPNE.add("Add Customer", add.pnl);
        pnl.add(tPNE, "grow");
    }
}
