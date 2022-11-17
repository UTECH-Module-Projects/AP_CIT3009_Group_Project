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
package com.application.view.invoice.order;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.misc.EntityTime;
import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.models.tables.Product;
import com.application.view.ServerApp;
import com.application.view.invoice.IViewPNL;
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
public class IViewCartPNL {
    private final String title;
    private final IViewPNL iViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel subTotLBL, subTotValLBL, discLBL, discValLBL, gctLBL, gctValLBL, totLBL, totValLBL;
    private GUIEntityTable itemTBL;
    private int itemIndex = -1;
    private TableList<InvoiceItem, String> items;
    private Invoice invoice;

    public IViewCartPNL(String title, IViewPNL iViewPNL, Client client) {
        this.title = title;
        this.iViewPNL = iViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 60]10[grow 10][grow 30]", "[]5[]5[]5[]5[]"));
        pnl.setVisible(false);

        subTotLBL = new JLabel("Subtotal");
        discLBL = new JLabel("Discount");
        gctLBL = new JLabel("GCT (" + String.format("%.1f%%", (Invoice.GCT - 1) * 100) + ")");
        totLBL = new JLabel("Total");

        subTotValLBL = new JLabel();
        discValLBL = new JLabel();
        gctValLBL = new JLabel();
        totValLBL = new JLabel();

        items = new TableList<>(InvoiceItem.class, InvoiceItem.headers);

        itemTBL = new GUIEntityTable(InvoiceItem.headers, false);
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
    }

    private void setProperties() {
        Font totFont = new Font(subTotLBL.getFont().getFontName(), Font.BOLD, 15);
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cart"));

        subTotLBL.setFont(totFont);
        discLBL.setFont(totFont);
        gctLBL.setFont(totFont);
        totLBL.setFont(totFont);

        subTotValLBL.setFont(totFont);
        discValLBL.setFont(totFont);
        gctValLBL.setFont(totFont);
        totValLBL.setFont(totFont);
    }

    public void clear() {
        itemTBL.clear();
        itemIndex = -1;

        subTotValLBL.setText("");
        discValLBL.setText("");
        gctValLBL.setText("");
        totValLBL.setText("");
        pnl.setVisible(false);
    }

    public void update(int index) {
        invoice = ServerApp.invoices.get(index);
        items.refresh(invoice.getItems());
        itemTBL.refresh(items.to2DTableArray());
        calcTot();
        pnl.setVisible(true);
    }

    private void calcTot() {
        double total, subTotal = 0, discount = 0, gct;
        Customer customer = invoice.getCustomer();
        EntityDate date = EntityDate.today();

        invoice.setItems(items.stream().toList());

        for (InvoiceItem item : items) {
            subTotal += item.getUnitPrice() * item.getQuantity();
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
}
