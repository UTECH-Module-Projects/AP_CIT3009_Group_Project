package com.application.view.server.view;

import com.application.models.tables.Customer;
import com.application.view.ServerApp;
import com.application.view.sales.SOrderPNL;
import com.application.view.server.SEViewPNL;
import com.database.server.Client;
import com.database.server.Server;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@Getter
public class SEViewClientsPNL {

    private final String title;
    private final SEViewPNL seViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel clientLBL, clientValLBL;


    public SEViewClientsPNL(String title, SEViewPNL seViewPNL, Client client) {
        this.title = title;
        this.seViewPNL = seViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("ins 10, flowx", "[][]", "[]"));

        clientLBL = new JLabel("Clients:");
        clientValLBL = new JLabel();
    }

    private void addComponents() {
        pnl.add(clientLBL, "grow 0, split");
        pnl.add(clientValLBL, "grow");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Total Clients");
        pnl.setBorder(titledBorder);

        clientLBL.setFont(new Font(clientLBL.getFont().getFontName(), Font.PLAIN, 15));
        clientValLBL.setFont(new Font(clientLBL.getFont().getFontName(), Font.BOLD, 15));

        refresh();
    }

    public void refresh() {
        clientValLBL.setText(String.valueOf(client.getTotClients()));
    }
}
