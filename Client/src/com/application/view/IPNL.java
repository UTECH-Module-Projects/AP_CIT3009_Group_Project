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
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.invoice.IViewPNL;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class IPNL {
    private final Client client;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private IViewPNL view;

    /**
     * Primary Constructor
     * @param client
     */
    public IPNL(Client client) {
        this.client = client;
        initializeComponents();
        addComponents();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this panel is broken down into separate functioning panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 10 0"));
        tPNE = new JTabbedPane();

        view = new IViewPNL("Manage Invoices", this, client);
    }

    /**
     * adding swing components to the panel
     */
    private void addComponents() {
        tPNE.add("Place Order", view.getPnl());
        pnl.add(tPNE, "grow");
    }
}
