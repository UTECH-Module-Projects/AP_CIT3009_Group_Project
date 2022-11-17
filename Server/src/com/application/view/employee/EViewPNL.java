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
package com.application.view.employee;

import com.application.generic.TableList;
import com.application.models.tables.Employee;
import com.application.models.tables.Invoice;
import com.application.view.ServerApp;
import com.application.view.EPNL;
import com.application.view.employee.view.EViewFormPNL;
import com.application.view.employee.view.EViewInvoicePNL;
import com.application.view.employee.view.EViewSearchPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@Getter
public class EViewPNL implements ListSelectionListener {
    private final String title;
    private final EPNL ePNL;
    private final Client client;
    private final TableList<Invoice, Integer> invoices;
    private JPanel pnl;
    private EViewSearchPNL search;
    private EViewFormPNL form;
    private GUIEntityTable empTBL;
    private EViewInvoicePNL invPNL;
    @Setter
    private int empIndex = -1;

    /**
     * Custmer View Panel Default Constructor
     * @param ePNL
     */
    public EViewPNL(String title, EPNL ePNL, Client client) {
        this.title = title;
        this.ePNL = ePNL;
        this.client = client;
        this.invoices = new TableList<>(Invoice.class, Invoice.headers);
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this employee panel is broken down into separate panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid, grow]10[nogrid, grow 0]", "[grow 0]10[grow 0]"));

        empTBL = new GUIEntityTable(ServerApp.employees.to2DArray(), Employee.headers, true);
        search = new EViewSearchPNL(title, this, client);
        form = new EViewFormPNL(title,this, client);
        invPNL = new EViewInvoicePNL(title,this, client, invoices);
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(empTBL.getSPNE(), "grow, wrap");
        pnl.add(form.getPnl(), "grow");
        pnl.add(invPNL.getPnl(), "grow, wrap");
    }

    private void setProperties() {
        empTBL.addListSelectionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        form.clear();
        invPNL.clear();
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
                    empIndex = newIndex;
                    Employee employee = ServerApp.employees.get(empIndex);
                    invPNL.update(employee.getIdNum());
                    form.update(employee);
                    search.getPrint().setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
