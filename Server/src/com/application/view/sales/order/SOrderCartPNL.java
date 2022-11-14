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
package com.application.view.sales.order;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.misc.EntityTime;
import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.ServerApp;
import com.application.view.sales.SOrderPNL;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class SOrderCartPNL implements ActionListener, ListSelectionListener {
    private final String title;
    private final SOrderPNL sOrderPNL;
    private final Client client;
    private final Invoice invoice;
    private JPanel pnl;
    private JLabel custLBL, empLBL;
    private GUIEntityTable itemTBL;
    private JButton checkout, delete, cancel;
    private int itemIndex = -1;

    public SOrderCartPNL(String title, SOrderPNL sOrderPNL, Client client, Invoice invoice) {
        this.title = title;
        this.sOrderPNL = sOrderPNL;
        this.client = client;
        this.invoice = invoice;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[]10[]10[]", "[]5[]5[]5[]15[]"));
        pnl.setVisible(false);

        custLBL = new JLabel("Customer:");
        empLBL = new JLabel("Employee:");

        itemTBL = new GUIEntityTable(InvoiceItem.headers, true);

        checkout = new JButton("Checkout", new ImageIcon(ServerApp.salesIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
        cancel = new JButton("Cancel", new ImageIcon(ServerApp.cancelIMG));
    }

    private void addComponents() {
        pnl.add(custLBL, "grow, span, wrap");
        pnl.add(empLBL, "grow, span, wrap");
        pnl.add(itemTBL.getSPNE(), "grow, span, wrap");
        pnl.add(checkout, "center, span, split 3, width 100");
        pnl.add(delete, "center, width 100");
        pnl.add(cancel, "center, width 100, wrap");
    }

    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cart"));

        itemTBL.addListSelectionListener(this);

        checkout.setEnabled(false);
        delete.setEnabled(false);

        checkout.addActionListener(this);
        delete.addActionListener(this);
        cancel.addActionListener(this);
    }

    public void clear() {
        custLBL.setText("Customer:");
        empLBL.setText("Employee:");
        itemTBL.clear();
        itemIndex = -1;
        delete.setEnabled(false);
        checkout.setEnabled(false);
        pnl.setVisible(false);
    }

    public void update() {
        itemTBL.refresh(sOrderPNL.getForm().getItems().to2DArray());
        if (sOrderPNL.getForm().getItems().size() > 0) {
            custLBL.setText("Customer: " + invoice.getCustomer().getName());
            empLBL.setText("Employee: " + invoice.getEmployee().getName());
            checkout.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(checkout)) {
            double total = 0, discount = 0;
            Customer customer = invoice.getCustomer();

            EntityDate date = EntityDate.today();
            invoice.setItems(sOrderPNL.getForm().getItems());
            List<InvoiceItem> items = invoice.getItems();
            for (InvoiceItem item : items) {
                total += item.getUnitPrice() * item.getQuantity();
            }
            if (invoice.getCustomer().isMem()) {
                if (invoice.getCustomer().getDome().compare(date) == -1) {
                    customer.setMem(false);
                    customer.setDom(null);
                    customer.setDome(null);
                    boolean result = (boolean) client.cud("update", "Customer", customer);
                    if (result) {
                        client.log(Level.INFO, "updated customer. {" + customer.getIdNum() + "}");
                    } else {
                        client.log(Level.WARN, "could not update customer! {" + customer.getIdNum() + "}");
                    }
                }
            }
            if (customer.isMem()) {
                discount = total * 0.1;
            }

            total = (total - discount) * 1.165;
            invoice.setBillDate(date);
            invoice.setBillTime(EntityTime.now());
            invoice.setDiscount(discount);
            invoice.setTotal(total);
            boolean result = (boolean) client.cud("create", "Invoice", invoice);

            if (result) {
                client.log(Level.INFO, "created invoice. {" + invoice.getIdNum() + "}");

                sOrderPNL.getSPNL().resetInvoice();

                FileLocationSelector selector = new FileLocationSelector();
                String filePath = selector.getFilePath(sOrderPNL.getPnl(), invoice.getCustomer().getName() + " Invoice_" + date + ".pdf");
                if (filePath == null) return;

                List<Invoice> objList = new ArrayList<>();
                objList.add(invoice);
                if (ReportGenerator.invoiceReport(filePath, new TableList<>(Invoice.class, new HashSet<>(objList), Invoice.headers), InvoiceItem.headers)) {
                    JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Invoice successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);
                    sOrderPNL.clear();
                    if (!Desktop.isDesktopSupported()) {
                        JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Error when opening invoice!", title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Error when creating invoice!", title, JOptionPane.ERROR_MESSAGE);
                }
                selector.openFile(filePath, sOrderPNL.getPnl(), title);
            } else {
                client.log(Level.WARN, "Could not create invoice! {" + invoice.getIdNum() + "}");
                JOptionPane.showMessageDialog(pnl, "Could not place order! Try again...", title, JOptionPane.ERROR_MESSAGE);
                sOrderPNL.getSPNL().setInvID((int) client.genID("Invoice", Invoice.idLength));
                invoice.setIdNum(sOrderPNL.getSPNL().getInvID());
            }
        } else if (e.getSource().equals(delete)) {
            int[] selRows = itemTBL.getTbl().getSelectedRows();
            String s = (selRows.length > 1 ? "s" : "");

            if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Delete Item" + s + "?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int row : selRows) {
                    sOrderPNL.getForm().getItems().remove(row);
                }

                String resultS = selRows.length > 1 ? "s" : "";
                JOptionPane.showMessageDialog(sOrderPNL.getSPNL().getPnl(), selRows.length + " Invoice" + resultS + " successfully deleted!", title, JOptionPane.INFORMATION_MESSAGE);

                delete.setEnabled(false);
                itemIndex = -1;
                itemTBL.refresh(sOrderPNL.getForm().getItems().to2DArray());
                if (sOrderPNL.getForm().getItems().size() == 0) {
                    checkout.setEnabled(false);
                }
            }
        } else if (e.getSource().equals(cancel)) {
            if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Cancel Order?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                itemTBL.clearSelection();
                delete.setEnabled(false);
                checkout.setEnabled(false);
                itemIndex = -1;
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int rowIndex = itemTBL.getSelectedRow();

        if (!e.getValueIsAdjusting() && rowIndex != -1) {
            try {
                int newIndex = itemTBL.convertRowIndexToModel(rowIndex);
                if (newIndex != itemIndex) {
                    itemIndex = newIndex;
                    delete.setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
