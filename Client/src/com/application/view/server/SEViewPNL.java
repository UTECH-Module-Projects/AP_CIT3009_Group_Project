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
package com.application.view.server;

import com.application.models.tables.Log;
import com.application.view.SEPNL;
import com.application.view.ClientApp;
import com.application.view.server.view.SEViewClientsPNL;
import com.application.view.server.view.SEViewSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class SEViewPNL implements ListSelectionListener {
    private final String title;
    private final SEPNL lPNL;
    private final Client client;
    private JPanel pnl;
    private SEViewSearchPNL search;
    private SEViewClientsPNL clients;
    private GUIEntityTable logTBL;
    @Setter
    private int logIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param lPNL
     */
    public SEViewPNL(String title, SEPNL lPNL, Client client) {
        this.title = title;
        this.lPNL = lPNL;
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
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[grow]", "[grow 0]10[grow 0]10[grow]"));

        logTBL = new GUIEntityTable(ClientApp.logs.to2DTableArray(), Log.tblHeaders, true);
        search = new SEViewSearchPNL(title, this, client);
        clients = new SEViewClientsPNL(title, this, client);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(clients.getPnl(), "aligny top, grow 0");
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(logTBL.getSPNE(), "aligny top, grow");
    }

    private void setProperties() {
        logTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        search.clear();
        logTBL.clearSelection();
        logIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        logTBL.refresh(ClientApp.logs.to2DTableArray());
        search.refresh();
        clients.refresh();
        clear();
    }

    /**
     * Listens for a click on a row in the table and displays selected row in form to be manipulated
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = logTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = logTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != logIndex) {
                    logIndex = newIndex;
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
