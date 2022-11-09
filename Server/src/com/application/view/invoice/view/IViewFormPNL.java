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
package com.application.view.invoice.view;

import com.application.models.misc.Date;
import com.application.view.ServerApp;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.time.Year;
import java.util.stream.IntStream;

public class IViewFormPNL {
    public static final Integer[] days = IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new);
    public static final String[] months = Date.Months;
    public static final Integer[] years = IntStream.rangeClosed(Year.now().getValue()-150, Year.now().getValue()).boxed().toArray(Integer[]::new);

    public JPanel pnl;

    private JLabel idLBL, billDateLBL, empLBL, custLBL, phoneNumLBL, emailLBL;
    public JTextField idTXT, billDateTXT;
    public JComboBox<String> empCMB, custCMB;

    public JButton save, delete, clear;

    public IViewFormPNL() {
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("ins 10 10 10 10", "[]10[]10[]10[]", "[]5[]5[]5[]5[]5[]5[]5[]"));

        idLBL = new JLabel("ID Number:");
        billDateLBL = new JLabel("Billing Date:");
        empLBL = new JLabel("Employee:");
        custLBL = new JLabel("Customer:");

        idTXT = new JTextField();
        billDateTXT = new JTextField();
//        empCMB = new JComboBox<>()

        save = new JButton("Save", new ImageIcon(ServerApp.saveIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));

        idTXT.setEnabled(false);
    }

    private void addComponents() {
        pnl.add(idLBL, "grow");
        pnl.add(idTXT, "grow, span 2, wrap");
        pnl.add(billDateLBL, "grow");
        pnl.add(emailLBL, "grow");
        pnl.add(phoneNumLBL, "grow");
        pnl.add(save, "skip 1, width 100");
        pnl.add(delete, "width 100");
        pnl.add(clear, "wrap, width 100");
    }

    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Customer Form"));
    }
}
