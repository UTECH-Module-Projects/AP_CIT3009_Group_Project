package com.application.view;

import com.application.view.AdminPage;
import com.application.view.customer.CViewPNL;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class CPNL {
    private final AdminPage adminPage;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private CViewPNL view;

    public CPNL(AdminPage adminPage) {
        this.adminPage = adminPage;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill"));
        tPNE = new JTabbedPane();

        view = new CViewPNL(this);
    }

    private void addComponents() {
        tPNE.add("View Customer", view.getPnl());
//        tPNE.add("Add Customer", add.pnl);
        pnl.add(tPNE, "grow");
    }
}
