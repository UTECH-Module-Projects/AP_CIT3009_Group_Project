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
package com.application.view.customer;

import com.application.generic.TableList;
import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.view.CPNL;
import com.application.view.ServerApp;
import com.application.view.customer.view.CViewFormPNL;
import com.application.view.customer.view.CViewInvoicePNL;
import com.application.view.customer.view.CViewSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class CViewPNL implements ListSelectionListener {
    private final String title;
    private final CPNL cPNL;
    private final Client client;
    private final TableList<Invoice, Integer> invoices;
    private JPanel pnl;
    private CViewSearchPNL search;
    private CViewFormPNL form;
    private GUIEntityTable custTBL;
    private CViewInvoicePNL invPNL;
    @Setter
    private int custIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param cPNL
     */
    public CViewPNL(String title, CPNL cPNL, Client client) {
        this.title = title;
        this.cPNL = cPNL;
        this.client = client;
        this.invoices = new TableList<>(Invoice.class, Invoice.headers);
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this customer panel is broken down into separate panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid, grow]10[nogrid, grow 0]", "[grow 0]10[grow 0]"));

        custTBL = new GUIEntityTable(ServerApp.customers.to2DArray(), Customer.headers, true);
        search = new CViewSearchPNL(title, this, client);
        form = new CViewFormPNL(title,this, client);
        invPNL = new CViewInvoicePNL(title,this, client, invoices);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(custTBL.getSPNE(), "grow, wrap");
        pnl.add(form.getPnl(), "grow");
        pnl.add(invPNL.getPnl(), "grow, wrap");
    }

    private void setProperties() {
        custTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        form.clear();
        invPNL.clear();
        search.clear();
        custTBL.clearSelection();
        custIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        custTBL.refresh(ServerApp.customers.to2DArray());
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
                    Customer customer = ServerApp.customers.get(custIndex);
                    invPNL.update(customer.getIdNum());
                    form.update(customer);
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
