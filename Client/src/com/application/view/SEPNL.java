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

import com.application.view.server.SEViewPNL;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class SEPNL {
    private final Client client;
    @Setter
    private JPanel pnl;
    private JTabbedPane tPNE;
    private SEViewPNL view;

    public SEPNL(Client client) {
        this.client = client;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 10 0"));
        tPNE = new JTabbedPane();

        view = new SEViewPNL("Manage Logs", this, client);
    }

    private void addComponents() {
        tPNE.add(view.getTitle(), view.getPnl());
        pnl.add(tPNE, "grow");
    }


}
