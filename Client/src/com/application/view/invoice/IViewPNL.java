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
package com.application.view.invoice;

import com.application.models.tables.Invoice;
import com.application.view.IPNL;
import com.application.view.ClientApp;
import com.application.view.invoice.order.IViewCartPNL;
import com.application.view.invoice.order.IViewSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class IViewPNL implements ListSelectionListener {
    private final String title;
    private final IPNL iPNL;
    private final Client client;
    private JPanel pnl;
    private IViewSearchPNL search;
    private IViewCartPNL cart;
    private GUIEntityTable invTBL;
    @Setter
    private int invIndex = -1;


    /**
     * Custmer View Panel Default Constructor
     * @param iPNL
     */
    public IViewPNL(String title, IPNL iPNL, Client client) {
        this.title = title;
        this.iPNL = iPNL;
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
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid, grow]10[grow 0]", "[grow 0]10[grow]"));

        invTBL = new GUIEntityTable(ClientApp.invoices.to2DArray(), Invoice.headers, true);
        search = new IViewSearchPNL(title, this, client);
        cart = new IViewCartPNL(title,this, client);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx, growy 0");
        pnl.add(invTBL.getSPNE(), "aligny top, grow, wrap");
        pnl.add(cart.getPnl(), "grow 0");
    }

    private void setProperties() {
        invTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        search.clear();
        cart.clear();
        invIndex = -1;
        invTBL.clearSelection();
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        invTBL.refresh(ClientApp.invoices.to2DArray());
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
                    search.getDelete().setEnabled(true);
                    cart.update(invIndex);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
