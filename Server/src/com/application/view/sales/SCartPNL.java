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
package com.application.view.sales;

import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.SPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class SCartPNL {
    private final SPNL spnl;
    private final Client client;
    private JPanel pnl;
    private GUIEntityTable table;
    private JButton removeBTN;
    private JButton printBTN;
    private String title;

    private Invoice invoice;

    /**
     * Primary Constructor
     * @param spnl - the parent panel
     * @param client
     */
    public SCartPNL(String title, SPNL spnl, Client client) {
        this.spnl = spnl;
        this.client = client;
        this.title = title;
        this.initializeComponents();
        this.addComponentsToPanel();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this customer panel is broken down into separate panels
     */
    public void initializeComponents() {
        table = new GUIEntityTable(InvoiceItem.headers, true);
        pnl = new JPanel();
        pnl.setBounds(40, 80, 500, 500);
        pnl.setLayout(new MigLayout("ins 10 10 10 10,flowx,fill", "[nogrid,center]", "[nogrid]"));
        printBTN = new JButton("Create Invoice");
        removeBTN = new JButton("Remove Product");
    }

    /**
     * Adds swing components to panel
     */
    public void addComponentsToPanel() {
        pnl.add(table.getSPNE(), "wrap,span,growx");
        pnl.add(removeBTN, "width 100");
        pnl.add(printBTN, "width 100");
    }

    /**
     * Panel getter
     * @return
     */
    public JPanel getPnl() {
        return this.pnl;
    }
}
