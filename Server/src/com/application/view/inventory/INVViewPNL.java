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
package com.application.view.inventory;

import com.application.models.tables.Product;
import com.application.view.INVPNL;
import com.application.view.ServerApp;
import com.application.view.inventory.order.INVViewFormPNL;
import com.application.view.inventory.order.INVViewSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class INVViewPNL implements ListSelectionListener {
    private final String title;
    private final INVPNL invPNL;
    private final Client client;
    private JPanel pnl;
    private INVViewSearchPNL search;
    private INVViewFormPNL form;
    private GUIEntityTable prodTBL;
    @Setter
    private int prodIndex = -1;


    /**
     * Custmer View Panel Default Constructor
     * @param invPNL
     */
    public INVViewPNL(String title, INVPNL invPNL, Client client) {
        this.title = title;
        this.invPNL = invPNL;
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

        prodTBL = new GUIEntityTable(ServerApp.products.to2DArray(), Product.headers, true);
        search = new INVViewSearchPNL(title, this, client);
        form = new INVViewFormPNL(title,this, client);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx, growy 0");
        pnl.add(prodTBL.getSPNE(), "aligny top, grow, wrap");
        pnl.add(form.getPnl(), "grow 0");
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
        prodIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
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

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = prodTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != prodIndex) {
                    prodIndex = newIndex;
                    Product product = ServerApp.products.get(prodIndex);
                    form.update(product);
                    invPNL.getView().getSearch().getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
