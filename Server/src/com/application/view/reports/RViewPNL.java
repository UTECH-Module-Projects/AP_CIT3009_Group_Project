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
package com.application.view.reports;

import com.application.view.ServerApp;
import com.application.view.customer.CViewPNL;
import com.application.view.reports.view.*;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class RViewPNL {
    private final Client client;
    @Setter
    private JPanel pnl;
    private RCustomerPNL custRep;
    private REmployeePNL empRep;
    private RLogsPNL logRep;
    private RInvoicePNL invRep;
    private RProductsPNL prodRep;

    public RViewPNL(Client client) {
        this.client = client;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10", "[][][]", "[][]"));

        custRep = new RCustomerPNL("Customer Report", this, client);
        empRep = new REmployeePNL("Employee Report", this, client);
        logRep = new RLogsPNL("Log Report", this, client);
        invRep = new RInvoicePNL("Invoice Report", this, client);
        prodRep = new RProductsPNL("Product Report", this, client);
    }

    private void addComponents() {
        pnl.add(custRep.getPnl(), "center, grow, span, split 3");
        pnl.add(empRep.getPnl(), "center, grow");
        pnl.add(invRep.getPnl(), "center, grow, wrap");

        pnl.add(logRep.getPnl(), "center, grow, span, split 2");
        pnl.add(prodRep.getPnl(), "center, grow, wrap");
    }
}
