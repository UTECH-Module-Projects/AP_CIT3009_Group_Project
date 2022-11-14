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

import com.application.generic.TableList;
import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.view.invoice.IViewPNL;
import com.database.server.Client;
import jakarta.persistence.Table;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


@Getter
public class IPNL {
    private final ServerApp serverApp;
    private final Client client;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private IViewPNL view;


    /**
     * This creates the main invoice panel which allows the user to manage invoices
     * @param serverApp The main server frame
     * @param client The client
     */
    public IPNL(ServerApp serverApp, Client client) {
        this.serverApp = serverApp;
        this.client = client;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill"));
        tPNE = new JTabbedPane();
        view = new IViewPNL(this, client);
    }

    private void addComponents() {
        tPNE.add("View Invoice", view.getPnl());
//        tPNE.add("Add Customer", add.pnl);
        pnl.add(tPNE, "grow");
    }
}
