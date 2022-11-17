package com.application.view;

import com.application.view.reports.RViewPNL;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class RPNL {
    private JPanel pnl;
    private JTabbedPane tPNE;
    private RViewPNL view;
    //private IViewPNL view;
    private final Client client;


    public RPNL(Client client) {
        this.client = client;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 10 0"));
        tPNE = new JTabbedPane();

        view = new RViewPNL(client);
        //view = new IViewPNL(this, client);
    }

    private void addComponents() {
        tPNE.addTab("Manage Reports", view.getPnl());
        pnl.add(tPNE, "grow");
    }
}

