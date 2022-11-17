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
package com.application.view.customer.view;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.ClientApp;
import com.application.view.customer.CViewPNL;
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
public class CViewInvoicePNL implements ActionListener, ListSelectionListener {
    private final String title;
    private final CViewPNL cViewPNL;
    private final Client client;
    private final TableList<Invoice, Integer> invoices;
    private JPanel pnl;
    private JLabel totInvLBL, totSalesLBL, firstDateLBL, lastDateLBL;
    private GUIEntityTable invTBL;
    private JButton print, delete, clear;
    private String custID = "";
    private int invIndex = -1;

    public CViewInvoicePNL(String title, CViewPNL cViewPNL, Client client, TableList<Invoice, Integer> invoices) {
        this.title = title;
        this.cViewPNL = cViewPNL;
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

        print = new JButton("Print", new ImageIcon(ClientApp.printIMG));
        delete = new JButton("Delete", new ImageIcon(ClientApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ClientApp.clearIMG));
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
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Customer Invoices"));

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
        refresh();
        invTBL.clear();
        pnl.setVisible(false);
    }

    public void refresh() {
        invTBL.clearSelection();
        delete.setEnabled(false);
        print.setEnabled(false);
        invIndex = -1;
    }

    public void update(String custID) {
        if (!this.custID.equals(custID)) {
            invoices.refresh(ClientApp.invoices.filter(inv -> inv.getCustomer().getIdNum().equals(custID)));
            this.custID = custID;
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
            String filePath = selector.getFilePath(cViewPNL.getPnl(), custID + "_Invoice_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = invTBL.getTbl().getSelectedRows();

            TableList<Invoice, Integer> selInvoices = new TableList<>(Invoice.class, Invoice.headers);
            for (int row : rows) {
                selInvoices.add(invoices.get(invTBL.convertRowIndexToModel(row)));
            }

            if (ReportGenerator.invoiceReport(filePath, selInvoices, InvoiceItem.headers)) {
                JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Invoice Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, cViewPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            refresh();
            print.setSelected(false);
        } else if (e.getSource().equals(delete)) {
            int[] selRows = invTBL.getTbl().getSelectedRows();
            int invCount = 0;
            String s = (selRows.length > 1 ? "s" : "");

            if (JOptionPane.showConfirmDialog(cViewPNL.getCPNL().getPnl(), "Delete Invoice" + s + "?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int i = selRows.length-1; i >= 0; i--) {
                    int idNum = invoices.get(selRows[i]).getIdNum();
                    boolean result = (Boolean) client.cud("delete", "Invoice", idNum);

                    if (result) {
                        client.log(Level.INFO, "deleted invoice. {" + idNum + "}");
                        ++invCount;
                        invoices.remove(selRows[i]);
                    } else {
                        client.log(Level.WARN, "Could not delete invoice! {" + idNum + "}");
                    }
                }
                String resultMsg = (invCount == selRows.length ? (selRows.length > 1 ? "All " : "") : (invCount + " "));
                String resultS = (selRows.length > 1 ? (invCount > 1 ? "s" : "") : "");
                JOptionPane.showMessageDialog(cViewPNL.getCPNL().getPnl(), resultMsg + "Invoice" + resultS + " successfully deleted!", title, JOptionPane.INFORMATION_MESSAGE);
                update(this.custID);
                ClientApp.update("Invoice");
            }
            delete.setSelected(false);
        } else if (e.getSource().equals(clear)) {
            refresh();
            clear.setSelected(false);
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
