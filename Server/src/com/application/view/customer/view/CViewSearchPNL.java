package com.application.view.customer.view;

import com.application.view.ServerApp;
import com.application.view.customer.CViewPNL;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

@Getter
public class CViewSearchPNL {
    private final CViewPNL cViewPNL;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, addressLBL, phoneNumLBL, emailLBL;
    private JTextField idTXT, nameTXT, addressTXT, phoneNumTXT, emailTXT;
    private JButton clear, refresh;
    public CViewSearchPNL(CViewPNL cViewPNL) {
        this.cViewPNL = cViewPNL;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10, gapx 10", "[grow 10][grow 10][grow 10][grow 10][grow 10][grow 6][grow 14][grow 10][grow 20]", "[][][][]10[]"));
        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Customer Name:");
        addressLBL = new JLabel("Address:");
        phoneNumLBL = new JLabel("Phone Number:");
        emailLBL = new JLabel("Email:");

        idTXT = new JTextField();
        nameTXT = new JTextField();
        addressTXT = new JTextField();
        phoneNumTXT = new JTextField();
        emailTXT = new JTextField();

        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
        refresh = new JButton("Refresh", new ImageIcon(ServerApp.refIMG));
    }

    private void addComponents() {
        pnl.add(idLBL, "grow, span 2");
        pnl.add(nameLBL, "grow, span 3, wrap");
        pnl.add(idTXT, "grow, span 2");
        pnl.add(nameTXT, "grow, span 3, wrap");

        pnl.add(addressLBL, "grow, span 4");
        pnl.add(phoneNumLBL, "grow, span 2");
        pnl.add(emailLBL, "gapx 0, grow, span 2, wrap");
        pnl.add(addressTXT, "grow, span 4");
        pnl.add(phoneNumTXT, "grow, span 2");
        pnl.add(emailTXT, "gapx 0, grow, span 2, wrap");
        pnl.add(clear);
        pnl.add(refresh);
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
        pnl.setBorder(titledBorder);
    }
}
