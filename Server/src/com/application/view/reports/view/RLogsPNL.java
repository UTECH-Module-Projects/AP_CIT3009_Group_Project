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

import com.application.models.tables.Log;
import com.application.view.SEPNL;
import com.application.view.ServerApp;
import com.application.view.reports.RViewPNL;
import com.application.view.reports.view.logs.RLogsSearchPNL;
import com.application.view.server.view.SEViewClientsPNL;
import com.application.view.server.view.SEViewSearchPNL;
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
public class RLogsPNL implements ListSelectionListener {
    private final String title;
    private final RViewPNL rViewPNL;
    private final Client client;
    private JPanel pnl;
    private RLogsSearchPNL search;
    private GUIEntityTable logTBL;
    @Setter
    private int logIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param rViewPNL
     */
    public RLogsPNL(String title, RViewPNL rViewPNL, Client client) {
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
        pnl = new JPanel(new MigLayout("fill, flowy, ins 10", "[]", "[]10[]"));

        logTBL = new GUIEntityTable(ServerApp.logs.to2DTableArray(), Log.tblHeaders, true);
        search = new RLogsSearchPNL(title, this, client);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx, growy 0");
        pnl.add(logTBL.getSPNE(), "aligny top, grow");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
        pnl.setBorder(titledBorder);

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
        ServerApp.refresh("Log");
        logTBL.refresh(ServerApp.logs.to2DTableArray());
        search.refresh();
        logTBL.clearSelection();
        logIndex = -1;
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
