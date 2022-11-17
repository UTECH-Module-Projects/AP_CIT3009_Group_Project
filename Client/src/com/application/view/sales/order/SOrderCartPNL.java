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
import com.application.models.tables.Product;
import com.application.view.ClientApp;
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
    private final TableList<InvoiceItem, String> items;
    private final List<Integer> prodIndexes = new ArrayList<>();
    private JPanel pnl;
    private JLabel subTotLBL, subTotValLBL, discLBL, discValLBL, gctLBL, gctValLBL, totLBL, totValLBL;
    private GUIEntityTable itemTBL;
    private JButton checkout, delete, clear, cancel;
    private int itemIndex = -1;

    public SOrderCartPNL(String title, SOrderPNL sOrderPNL, Client client, Invoice invoice, TableList<InvoiceItem, String> items) {
        this.title = title;
        this.sOrderPNL = sOrderPNL;
        this.client = client;
        this.invoice = invoice;
        this.items = items;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 60]10[grow 10][grow 30]", "[]5[]5[]5[]5[]15[]"));
        pnl.setVisible(false);

        subTotLBL = new JLabel("Subtotal");
        discLBL = new JLabel("Discount");
        gctLBL = new JLabel("GCT (" + String.format("%.1f%%", (Invoice.GCT - 1) * 100) + ")");
        totLBL = new JLabel("Total");

        subTotValLBL = new JLabel();
        discValLBL = new JLabel();
        gctValLBL = new JLabel();
        totValLBL = new JLabel();

        itemTBL = new GUIEntityTable(InvoiceItem.headers, true);

        checkout = new JButton("Checkout", new ImageIcon(ClientApp.checkoutIMG));
        delete = new JButton("Delete", new ImageIcon(ClientApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ClientApp.clearIMG));
        cancel = new JButton("Cancel", new ImageIcon(ClientApp.cancelIMG));
    }

    private void addComponents() {
        pnl.add(itemTBL.getSPNE(), "span, wrap, height 400");
        pnl.add(subTotLBL, "skip 1");
        pnl.add(subTotValLBL, "wrap");

        pnl.add(discLBL, "skip 1");
        pnl.add(discValLBL, "wrap");

        pnl.add(gctLBL, "skip 1");
        pnl.add(gctValLBL, "wrap");

        pnl.add(totLBL, "skip 1");
        pnl.add(totValLBL, "wrap");

        pnl.add(checkout, "center, span, split 4, width 100");
        pnl.add(delete, "center, width 100");
        pnl.add(clear, "center, width 100");
        pnl.add(cancel, "center, width 100, wrap");
    }

    private void setProperties() {
        Font totFont = new Font(subTotLBL.getFont().getFontName(), Font.BOLD, 15);
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cart"));

        itemTBL.addListSelectionListener(this);

        subTotLBL.setFont(totFont);
        discLBL.setFont(totFont);
        gctLBL.setFont(totFont);
        totLBL.setFont(totFont);

        subTotValLBL.setFont(totFont);
        discValLBL.setFont(totFont);
        gctValLBL.setFont(totFont);
        totValLBL.setFont(totFont);

        checkout.setEnabled(false);
        delete.setEnabled(false);
        clear.setEnabled(false);

        checkout.addActionListener(this);
        delete.addActionListener(this);
        clear.addActionListener(this);
        cancel.addActionListener(this);
    }

    public void clear() {
        itemTBL.clear();
        prodIndexes.clear();
        items.clear();
        itemIndex = -1;

        subTotValLBL.setText("");
        discValLBL.setText("");
        gctValLBL.setText("");
        totValLBL.setText("");

        delete.setEnabled(false);
        checkout.setEnabled(false);
    }

    public void update() {
        itemTBL.refresh(items.to2DArray());
        checkout.setEnabled(items.size() > 0);
        calcTot();
        itemIndex = -1;
        delete.setEnabled(false);
        clear.setEnabled(false);
    }

    public void calcTot() {
        double total, subTotal = 0, discount = 0, gct;
        Customer customer = invoice.getCustomer();
        EntityDate date = EntityDate.today();

        invoice.setItems(items.stream().toList());

        for (InvoiceItem item : items) {
            subTotal += item.getUnitPrice() * item.getQuantity();
        }

        if (customer.isMem()) {
            if (customer.getDome().compare(date) == -1) {
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
            discount = subTotal * Customer.discount;
        }

        gct = (subTotal - discount) * (Invoice.GCT - 1);
        total = (subTotal - discount) * Invoice.GCT;

        subTotValLBL.setText(String.format("$%.2f", subTotal));
        discValLBL.setText(String.format("$%.2f", discount));
        gctValLBL.setText(String.format("$%.2f", gct));
        totValLBL.setText(String.format("$%.2f", total));

        invoice.setBillDate(date);
        invoice.setBillTime(EntityTime.now());
        invoice.setDiscount(discount);
        invoice.setTotal(total);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(clear)) {
            itemTBL.clearSelection();
            itemIndex = -1;
            clear.setEnabled(false);
            clear.setSelected(false);
        } else if (e.getSource().equals(checkout)) {
            if (JOptionPane.showConfirmDialog(sOrderPNL.getPnl(), "Checkout Order?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                EntityDate date = EntityDate.today();
                invoice.setItems(items.stream().toList());

                for (int i = 0; i < items.size(); i++) {
                    Product product = ClientApp.products.get(prodIndexes.get(i));
                    product.setStock(product.getStock() - items.get(i).getQuantity());
                    product.setTotSold(product.getTotSold() + items.get(i).getQuantity());

                    boolean prodRes = (boolean) client.cud("update", "Product", product);
                    if (prodRes) {
                        client.log(Level.INFO, "updated product. {" + product.getIdNum() + "}");
                    } else {
                        client.log(Level.WARN, "could not update product! {" + product.getIdNum() + "}");
                    }
                }

                boolean result = (boolean) client.cud("create", "Invoice", invoice);
                if (result) {
                    client.log(Level.INFO, "created invoice. {" + invoice.getIdNum() + "}");

                    ClientApp.invoices.add(invoice);
                    ClientApp.iPNL.getView().getInvTBL().refresh(ClientApp.invoices.to2DArray());
                    sOrderPNL.getProdTBL().refresh(ClientApp.products.to2DArray());

                    JOptionPane.showMessageDialog(sOrderPNL.getSPNL().getPnl(), "Checkout successful!", title, JOptionPane.INFORMATION_MESSAGE);

                    FileLocationSelector selector = new FileLocationSelector();
                    String filePath = selector.getFilePath(sOrderPNL.getPnl(), invoice.getIdNum() + "_Invoice_" + date + ".pdf");
                    if (filePath != null) {
                        List<Invoice> objList = new ArrayList<>();
                        objList.add(invoice);
                        if (ReportGenerator.invoiceReport(filePath, new TableList<>(Invoice.class, new HashSet<>(objList), Invoice.headers), InvoiceItem.headers)) {
                            JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Invoice successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);
                            if (!Desktop.isDesktopSupported()) {
                                JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Error when opening invoice!", title, JOptionPane.ERROR_MESSAGE);
                            } else selector.openFile(filePath, sOrderPNL.getPnl(), title);
                        } else {
                            JOptionPane.showMessageDialog(sOrderPNL.getPnl(), "Error when printing invoice!", title, JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    sOrderPNL.getSPNL().resetInvoice();

                    ClientApp.update("Invoice");
                    ClientApp.update("Product");
                } else {
                    client.log(Level.WARN, "Could not create invoice! {" + invoice.getIdNum() + "}");
                    JOptionPane.showMessageDialog(pnl, "Could not checkout! Try again...", title, JOptionPane.ERROR_MESSAGE);
                    invoice.setIdNum((int) client.genID("Invoice", Invoice.idLength));
                }
            }
            checkout.setSelected(false);
        } else if (e.getSource().equals(delete)) {
            int[] selRows = itemTBL.getTbl().getSelectedRows();
            String s = (selRows.length > 1 ? "s" : "");

            if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Delete Item" + s + "?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                for (int i = selRows.length-1; i >= 0; i--) {
                    items.remove(selRows[i]);
                    prodIndexes.remove(selRows[i]);
                }
                JOptionPane.showMessageDialog(sOrderPNL.getSPNL().getPnl(), selRows.length + " Item" + s + " successfully deleted!", title, JOptionPane.INFORMATION_MESSAGE);

                update();
            }
            delete.setSelected(false);
        } else if (e.getSource().equals(cancel)) {
            if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Cancel Order?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                sOrderPNL.clear();
            }
            cancel.setSelected(false);
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
                    clear.setEnabled(true);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }
    }
}
