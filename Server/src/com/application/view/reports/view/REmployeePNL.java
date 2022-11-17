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

import com.application.generic.TableList;
import com.application.models.tables.Employee;
import com.application.models.tables.Invoice;
import com.application.view.EPNL;
import com.application.view.ServerApp;
import com.application.view.reports.RViewPNL;
import com.application.view.reports.view.employee.REmployeeSearchPNL;
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
public class REmployeePNL implements ListSelectionListener {
    private final String title;
    private final RViewPNL rViewPNL;
    private final Client client;
    private JPanel pnl;
    private REmployeeSearchPNL search;
    private GUIEntityTable empTBL;
    @Setter
    private int empIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param rViewPNL
     */
    public REmployeePNL(String title, RViewPNL rViewPNL, Client client) {
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

        search = new REmployeeSearchPNL(title, this, client);
        empTBL = new GUIEntityTable(ServerApp.employees.to2DArray(), Employee.headers, true);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(empTBL.getSPNE(), "aligny top, grow, wrap");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title);
        pnl.setBorder(titledBorder);

        empTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        search.clear();
        empTBL.clearSelection();
        empIndex = -1;
    }

    /**
     * Method fetches table from database and displays in jTable
     */
    public void refresh() {
        empTBL.refresh(ServerApp.employees.to2DArray());
        clear();
    }

    /**
     * Listens for a click on a row in the table and displays selected row in form to be manipulated
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = empTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = empTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != empIndex) {
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
