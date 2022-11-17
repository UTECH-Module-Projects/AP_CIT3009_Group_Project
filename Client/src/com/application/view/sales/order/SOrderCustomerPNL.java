package com.application.view.sales.order;

import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.view.ClientApp;
import com.application.view.sales.SOrderPNL;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@Getter
public class SOrderCustomerPNL implements ItemListener {

    private final String title;
    private final SOrderPNL sOrderPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel custLBL;
    private JComboBox<String> custNames;
    private final Invoice invoice;


    public SOrderCustomerPNL(String title, SOrderPNL sOrderPNL, Client client, Invoice invoice) {
        this.title = title;
        this.sOrderPNL = sOrderPNL;
        this.client = client;
        this.invoice = invoice;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("ins 10, flowx", "[]", "[]"));

        custLBL = new JLabel("Customer:");
        custNames = new JComboBox<>();
    }

    private void addComponents() {
        pnl.add(custLBL, "grow 0, split");
        pnl.add(custNames, "grow 0, width 200, wrap");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Customer");
        pnl.setBorder(titledBorder);

        refresh();

        custNames.addItemListener(this);
    }

    public void clear() {
        custNames.setSelectedIndex(0);
    }

    public void refresh() {
        custNames.setModel(new JComboBox<>(ClientApp.customers.map(Customer::getName).toArray(new String[0])).getModel());
        custNames.insertItemAt("", 0);
        custNames.setSelectedIndex(0);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(custNames)) {
            if (custNames.getSelectedIndex() == 0) {
                sOrderPNL.getForm().getPnl().setVisible(false);
                sOrderPNL.getCart().getPnl().setVisible(false);
            } else {
                invoice.setCustomer(ClientApp.customers.get(custNames.getSelectedIndex()-1));
                sOrderPNL.getForm().getPnl().setVisible(true);
                sOrderPNL.getCart().getPnl().setVisible(true);
                sOrderPNL.getCart().calcTot();
            }
        }
    }
}
