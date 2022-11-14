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

import com.application.models.misc.EntityDate;
import com.application.models.tables.Invoice;
import com.application.view.sales.SCartPNL;
import com.application.view.inventory.SCheckoutPNL;
import com.application.view.sales.SOrderPNL;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class SPNL {
    private final ServerApp serverApp;
    private final Client client;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private SCartPNL cart;
    private SOrderPNL order;
    @Setter
    private Invoice invoice;
    @Setter
    private int invID;

    /**
     * Primary Constructor
     * @param serverApp
     * @param client
     */
    public SPNL(ServerApp serverApp, Client client) {
        this.serverApp = serverApp;
        this.client = client;
        invID = (int) client.genID("Invoice", Invoice.idLength);
        resetInvoice();
        initializeComponents();
        addComponents();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this panel is broken down into separate functioning panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill"));
        tPNE = new JTabbedPane();

        order = new SOrderPNL("Place Order", this, client, invoice);
    }

    /**
     * adding swing components to the panel
     */
    private void addComponents() {
        tPNE.add("Place Order", order.getPnl());
        pnl.add(tPNE, "grow");
    }

    public void resetInvoice() {
        invID = (int) client.genID("Invoice", Invoice.idLength);
        invoice = new Invoice();
        invoice.setIdNum(invID);
        invoice.setEmployee(ServerApp.logEmp);
    }
}
