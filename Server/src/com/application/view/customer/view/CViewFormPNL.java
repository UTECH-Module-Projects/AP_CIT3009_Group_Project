package com.application.view.customer.view;

import com.application.models.misc.Date;
import com.application.view.ServerApp;
import com.application.view.customer.CViewPNL;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.time.Year;
import java.util.stream.IntStream;

public class CViewFormPNL {
    private final CViewPNL cViewPNL;
    public static final Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
    public static final String[] months = Date.Months;
    public static final Integer[] years = IntStream.rangeClosed(Year.now().getValue()-150, Year.now().getValue()).boxed().toArray(Integer[]::new);

    public JPanel pnl;

    private JLabel idLBL, nameLBL, dobLBL, addressLBL, phoneNumLBL, emailLBL, memLBL;
    public JTextField idTXT, nameTXT, phoneNumTXT, emailTXT;
    public JTextArea addressTXTA;
    public CViewFormDateCMB dob;
    public CViewFormMemBTN isMem;

    public JButton save, delete, clear;

    public CViewFormPNL(CViewPNL cViewPNL) {
        this.cViewPNL = cViewPNL;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("ins 10 10 10 10", "[]10[]10[]10[]", "[]5[]5[]5[]5[]5[]5[]5[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Full Name:");
        dobLBL = new JLabel("Date of Birth:");
        emailLBL = new JLabel("Email:");
        phoneNumLBL = new JLabel("Phone Number:");
        addressLBL = new JLabel("Address:");
        memLBL = new JLabel("Is a Member?");

        idTXT = new JTextField();
        nameTXT = new JTextField();
        dob = new CViewFormDateCMB();
        emailTXT = new JTextField();
        phoneNumTXT = new JTextField();
        addressTXTA = new JTextArea(5, 30);
        isMem = new CViewFormMemBTN();

        save = new JButton("Save", new ImageIcon(ServerApp.saveIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));

        idTXT.setEnabled(false);
    }

    private void addComponents() {
        pnl.add(idLBL, "grow");
        pnl.add(idTXT, "grow, span 2, wrap");
        pnl.add(nameLBL, "grow");
        pnl.add(nameTXT, "grow, span 3, wrap");
        pnl.add(dobLBL, "grow");
        pnl.add(dob.day, "grow");
        pnl.add(dob.month, "grow");
        pnl.add(dob.year, "grow, wrap");
        pnl.add(emailLBL, "grow");
        pnl.add(emailTXT, "grow, span 3, wrap");
        pnl.add(phoneNumLBL, "grow");
        pnl.add(phoneNumTXT, "grow, span 2, wrap");
        pnl.add(addressLBL, "grow");
        pnl.add(addressTXTA, "grow, span 3, wrap");
        pnl.add(memLBL, "grow");
        pnl.add(isMem.yes, "grow");
        pnl.add(isMem.no, "grow, wrap");
        pnl.add(save, "skip 1, width 100");
        pnl.add(delete, "width 100");
        pnl.add(clear, "wrap, width 100");
    }

    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Customer Form"));
    }
}
