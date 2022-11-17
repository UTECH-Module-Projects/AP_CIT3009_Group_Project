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

import com.application.generic.TableList;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.models.tables.Product;
import com.application.view.SPNL;
import com.application.view.ServerApp;
import com.application.view.sales.order.SOrderCartPNL;
import com.application.view.sales.order.SOrderCustomerPNL;
import com.application.view.sales.order.SOrderFormPNL;
import com.application.view.sales.order.SOrderSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class SOrderPNL implements ListSelectionListener {
    private final String title;
    private final SPNL sPNL;
    private final Client client;
    private final Invoice invoice;
    private final TableList<InvoiceItem, String> items;
    private JPanel pnl;
    private SOrderSearchPNL search;
    private SOrderFormPNL form;
    private SOrderCustomerPNL selCust;
    private SOrderCartPNL cart;
    private GUIEntityTable prodTBL;
    @Setter
    private int prodIndex = -1;


    /**
     * Custmer View Panel Default Constructor
     * @param sPNL
     */
    public SOrderPNL(String title, SPNL sPNL, Client client, Invoice invoice, TableList<InvoiceItem, String> items) {
        this.title = title;
        this.sPNL = sPNL;
        this.client = client;
        this.invoice = invoice;
        this.items = items;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this employee panel is broken down into separate panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("flowy, ins 13 10 0 10", "[grow]10[nogrid, grow 0]", "[grow 0]10[grow 0]10[grow]"));

        prodTBL = new GUIEntityTable(ServerApp.products.to2DArray(), Product.headers, true);
        selCust = new SOrderCustomerPNL(title,this, client, invoice);
        search = new SOrderSearchPNL(title, this, client, invoice);
        form = new SOrderFormPNL(title,this, client, invoice, items);
        cart = new SOrderCartPNL(title,this, client, invoice, items);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(selCust.getPnl(), "aligny top, grow 0");
        pnl.add(search.getPnl(), "aligny top, growx, growy 0");
        pnl.add(prodTBL.getSPNE(), "aligny top, grow, wrap");
        pnl.add(form.getPnl(), "grow");
        pnl.add(cart.getPnl(), "grow, wrap");
    }

    private void setProperties() {
        prodTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        form.clear();
        search.clear();
        cart.clear();
        selCust.clear();
        prodIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        selCust.refresh();
        prodTBL.refresh(ServerApp.products.to2DArray());
        clear();
    }

    /**
     * Listens for a click on a row in the table and displays selected row in form to be manipulated
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = prodTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1 && selCust.getCustNames().getSelectedIndex() != 0) {
            try {
                int newIndex = prodTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != prodIndex) {
                    prodIndex = newIndex;
                    Product product = ServerApp.products.get(prodIndex);

                    if (product.getStock() <= 0) {
                        JOptionPane.showMessageDialog(sPNL.getPnl(), "No Stock Available for " + product.getName() + "!", title, JOptionPane.ERROR_MESSAGE);
                    } else {
                        form.update(product);
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
