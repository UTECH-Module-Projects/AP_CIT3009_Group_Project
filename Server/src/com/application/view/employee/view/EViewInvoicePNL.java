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
package com.application.view.employee.view;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.ServerApp;
import com.application.view.employee.EViewPNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.GUIEntityTable;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class EViewInvoicePNL implements ActionListener, ListSelectionListener {
    private final String title;
    private final EViewPNL eViewPNL;
    private final Client client;
    private final TableList<Invoice, Integer> invoices;
    private JPanel pnl;
    private JLabel totInvLBL, totSalesLBL, firstDateLBL, lastDateLBL;
    private GUIEntityTable invTBL;
    private JButton print, delete, clear;
    private String empID = "";
    private int invIndex = -1;

    public EViewInvoicePNL(String title, EViewPNL eViewPNL, Client client, TableList<Invoice, Integer> invoices) {
        this.title = title;
        this.eViewPNL = eViewPNL;
        this.client = client;
        this.invoices = invoices;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 45][grow 10][grow 45]", "[]5[]5[]15[]"));
        pnl.setVisible(false);

        totInvLBL = new JLabel("Count:");
        totSalesLBL = new JLabel("Sales:");
        firstDateLBL = new JLabel("First Date:");
        lastDateLBL = new JLabel("Last Date:");

        invTBL = new GUIEntityTable(Invoice.headers, true);

        print = new JButton("Print", new ImageIcon(ServerApp.printIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
    }

    private void addComponents() {
        pnl.add(totInvLBL, "grow");
        pnl.add(firstDateLBL, "skip 1, grow, right, wrap");
        pnl.add(totSalesLBL, "grow");
        pnl.add(lastDateLBL, "skip 1, grow, right, wrap");
        pnl.add(invTBL.getSPNE(), "span, wrap");
        pnl.add(print, "right, width 100");
        pnl.add(delete, "center, width 100");
        pnl.add(clear, "left, width 100, wrap");
    }

    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Employee Invoices"));

        invTBL.addListSelectionListener(this);

        print.setEnabled(false);
        delete.setEnabled(false);

        print.addActionListener(this);
        delete.addActionListener(this);
        clear.addActionListener(this);
    }

    public void clear() {
        totInvLBL.setText("Count:");
        totSalesLBL.setText("Sales:");
        firstDateLBL.setText("First Date:");
        lastDateLBL.setText("Last Date:");
        invTBL.clear();
        invIndex = -1;
        delete.setEnabled(false);
        print.setEnabled(false);
        pnl.setVisible(false);
    }

    public void update(String empID) {
        if (!this.empID.equals(empID)) {
            invoices.refresh(ServerApp.invoices.filter(inv -> inv.getEmployee().getIdNum().equals(empID)));
            this.empID = empID;
        }

        int totInv = invoices.size();

        if (totInv > 0) {
            double totSales = invoices.get(0).getTotal();
            EntityDate firstEntityDate = invoices.get(0).getBillDate(), lastEntityDate = invoices.get(0).getBillDate();

            for (int i = 1; i < totInv; i++) {
                totSales += invoices.get(i).getTotal();
                EntityDate billEntityDate = invoices.get(i).getBillDate();
                if (billEntityDate.compare(firstEntityDate) == -1)
                    firstEntityDate = billEntityDate;
                if (billEntityDate.compare(lastEntityDate) == 1)
                    lastEntityDate = billEntityDate;
            }

            totInvLBL.setText("Count: " + totInv);
            totSalesLBL.setText("Sales: " + String.format("$%.2f", totSales));

            firstDateLBL.setText("First Date: " + firstEntityDate.toString());
            lastDateLBL.setText("Last Date: " + lastEntityDate.toString());
            invTBL.refresh(invoices.to2DArray());
            pnl.setVisible(true);
        } else clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(print)) {
            EntityDate date = EntityDate.today();
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(eViewPNL.getPnl(), empID + " Invoice_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = invTBL.getTbl().getSelectedRows();

            TableList<Invoice, Integer> selInvoices = new TableList<>(Invoice.class, Invoice.headers);
            for (int row : rows) {
                selInvoices.add(invoices.get(invTBL.convertRowIndexToModel(row)));
            }

            if (ReportGenerator.invoiceReport(filePath, selInvoices, InvoiceItem.headers)) {
                JOptionPane.showMessageDialog(eViewPNL.getPnl(), "Invoice Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(eViewPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(eViewPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            selector.openFile(filePath, eViewPNL.getPnl(), title);
        } else if (e.getSource().equals(delete)) {
            int[] selRows = invTBL.getTbl().getSelectedRows();
            int invCount = 0;
            String s = (selRows.length > 1 ? "s" : "");

            if (JOptionPane.showConfirmDialog(eViewPNL.getEPNL().getPnl(), "Delete Invoice" + s + "?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int row : selRows) {
                    int idNum = invoices.get(row).getIdNum();
                    boolean result = (Boolean) client.cud("delete", "Invoice", idNum);

                    if (result) {
                        client.log(Level.INFO, "deleted invoice. {" + idNum + "}");
                        ++invCount;
                        invoices.remove(row);
                    } else {
                        client.log(Level.WARN, "Could not delete invoice! {" + idNum + "}");
                    }
                }
                String resultMsg = (invCount == selRows.length ? (selRows.length > 1 ? "All " : "") : (invCount + " "));
                String resultS = (selRows.length > 1 ? (invCount > 1 ? "s" : "") : "");
                JOptionPane.showMessageDialog(eViewPNL.getEPNL().getPnl(), resultMsg + "Invoice" + resultS + " successfully deleted!", title, JOptionPane.INFORMATION_MESSAGE);
                update(this.empID);
            }
        } else if (e.getSource().equals(clear)) {
            invTBL.clearSelection();
            delete.setEnabled(false);
            print.setEnabled(false);
            invIndex = -1;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = invTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = invTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != invIndex) {
                    invIndex = newIndex;
                    delete.setEnabled(true);
                    print.setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
