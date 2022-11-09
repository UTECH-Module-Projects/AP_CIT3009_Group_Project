package com.application.view;

import com.application.view.AdminPage;
import com.application.view.invoice.IViewPNL;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;


@Getter
public class IPNL {
    private final AdminPage adminPage;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private IViewPNL view;


    public IPNL(AdminPage adminPage) {
        this.adminPage = adminPage;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill"));
        tPNE = new JTabbedPane();

        view = new IViewPNL(this);
    }

    private void addComponents() {
        tPNE.add("View Invoice", view.pnl);
//        tPNE.add("Add Customer", add.pnl);
        pnl.add(tPNE, "grow");
    }
}
