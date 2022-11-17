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

import com.application.models.tables.Customer;
import com.application.view.ClientApp;
import com.application.view.reports.RViewPNL;
import com.application.view.reports.view.customer.RCustomerSearchPNL;
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
public class RCustomerPNL implements ListSelectionListener {
    private final String title;
    private final RViewPNL rViewPNL;
    private final Client client;
    private JPanel pnl;
    private RCustomerSearchPNL search;
    private GUIEntityTable custTBL;
    @Setter
    private int custIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param rViewPNL
     */
    public RCustomerPNL(String title, RViewPNL rViewPNL, Client client) {
        this.title = title;
        this.rViewPNL = rViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this customer panel is broken down into separate panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10", "[]", "[]10[]"));

        search = new RCustomerSearchPNL(title, this, client);
        custTBL = new GUIEntityTable(ClientApp.customers.to2DArray(), Customer.headers, true);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx, growy 0, wrap");
        pnl.add(custTBL.getSPNE(), "aligny top, grow");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
        pnl.setBorder(titledBorder);

        custTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        search.clear();
        custTBL.clearSelection();
        custIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        custTBL.refresh(ClientApp.customers.to2DArray());
        clear();
    }

    /**
     * Listens for a click on a row in the table and displays selected row in form to be manipulated
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = custTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = custTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != custIndex) {
                    custIndex = newIndex;
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
