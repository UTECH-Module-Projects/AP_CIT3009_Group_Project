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
package com.application.view.reports.view;

import com.application.models.tables.Invoice;
import com.application.view.ServerApp;
import com.application.view.reports.RViewPNL;
import com.application.view.reports.view.invoice.RInvoiceSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class RInvoicePNL implements ListSelectionListener {
    private final String title;
    private final RViewPNL rViewPNL;
    private final Client client;
    private JPanel pnl;
    private RInvoiceSearchPNL search;
    private GUIEntityTable invTBL;
    @Setter
    private int invIndex = -1;


    /**
     * Custmer View Panel Default Constructor
     * @param rViewPNL
     */
    public RInvoicePNL(String title, RViewPNL rViewPNL, Client client) {
        this.title = title;
        this.rViewPNL = rViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this employee panel is broken down into separate panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowy, ins 10", "[]", "[]10[]"));

        invTBL = new GUIEntityTable(ServerApp.invoices.to2DArray(), Invoice.headers, true);
        search = new RInvoiceSearchPNL(title, this, client);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx, growy 0");
        pnl.add(invTBL.getSPNE(), "aligny top, grow, wrap");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
        pnl.setBorder(titledBorder);

        invTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        search.clear();
        invTBL.clearSelection();
        invIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        invTBL.refresh(ServerApp.invoices.to2DArray());
        search.refresh();
        clear();
    }

    /**
     * Listens for a click on a row in the table and displays selected row in form to be manipulated
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = invTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = invTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != invIndex) {
                    invIndex = newIndex;
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
