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
package com.application.view.reports.view.employee;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Employee;
import com.application.models.tables.Invoice;
import com.application.view.ServerApp;
import com.application.view.employee.EViewPNL;
import com.application.view.reports.view.REmployeePNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.InvalidCharListener;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Getter
public class REmployeeSearchPNL implements ActionListener {
    private static final InvalidCharListener charListener = new InvalidCharListener(new char[]{'\\', '(', ')', '*', '.', '?', '|', '+', '$', '^'});

    private final String title;
    private final REmployeePNL rEmployeePNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, addressLBL, phoneNumLBL, emailLBL, typeLBL, depLBL;
    private JTextField idTXT, nameTXT, addressTXT, phoneNumTXT, emailTXT;
    private JComboBox<String> typeCMB, depCMB;
    private JButton print, refresh, clear;

    /**
     * Primary Constructor
     * @param title
     * @param rEmployeePNL
     * @param client
     */
    public REmployeeSearchPNL(String title, REmployeePNL rEmployeePNL, Client client) {
        this.title = title;
        this.rEmployeePNL = rEmployeePNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
        addTextSearchListeners();
    }

    /**
     * Initializes swing Components used in this search panel
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10, gapx 10", "[grow 10][grow 50][grow 40]", "[][][][]5[]15[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Employee Name:");
        addressLBL = new JLabel("Address:");
        phoneNumLBL = new JLabel("Phone Number:");
        emailLBL = new JLabel("Email:");
        typeLBL = new JLabel("Type:");
        depLBL = new JLabel("Department:");

        idTXT = new JTextField();
        nameTXT = new JTextField();
        addressTXT = new JTextField();
        phoneNumTXT = new JTextField();
        emailTXT = new JTextField();

        typeCMB = new JComboBox<>(Employee.types);
        depCMB = new JComboBox<>(ServerApp.depNames);

        print = new JButton("Print", new ImageIcon(ServerApp.printIMG));
        refresh = new JButton("Refresh", new ImageIcon(ServerApp.refIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL, "grow");
        pnl.add(nameLBL, "grow");
        pnl.add(phoneNumLBL, "grow, wrap");

        pnl.add(idTXT, "grow");
        pnl.add(nameTXT, "grow");
        pnl.add(phoneNumTXT, "grow, wrap");

        pnl.add(addressLBL, "grow, span 2");
        pnl.add(emailLBL, "grow, wrap");

        pnl.add(addressTXT, "grow, span 2");
        pnl.add(emailTXT, "grow, wrap");

        pnl.add(depLBL, "span 2, split 4");
        pnl.add(depCMB, "gapright 10");

        pnl.add(typeLBL);
        pnl.add(typeCMB, "wrap");

        pnl.add(print, "width 100, span, split 3, left, gapright 10");
        pnl.add(refresh, "width 100, left, gapright 10");
        pnl.add(clear, "width 100, left, wrap");
    }

    /**
     * Sets properties of components
     */
    private void setProperties() {
        pnl.setBorder(BorderFactory.createEtchedBorder());

        typeCMB.insertItemAt("", 0);
        depCMB.insertItemAt("", 0);

        typeCMB.setSelectedIndex(0);
        depCMB.setSelectedIndex(0);

        print.setEnabled(false);

        typeCMB.addActionListener(this);
        depCMB.addActionListener(this);
        print.addActionListener(this);
        refresh.addActionListener(this);
        clear.addActionListener(this);

        idTXT.addKeyListener(charListener);
        nameTXT.addKeyListener(charListener);
        addressTXT.addKeyListener(charListener);
        phoneNumTXT.addKeyListener(charListener);
        emailTXT.addKeyListener(charListener);
    }

    private void addTextSearchListeners() {
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setRowFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setRowFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setRowFilters();
            }
        };
        idTXT.getDocument().addDocumentListener(dl);
        nameTXT.getDocument().addDocumentListener(dl);
        addressTXT.getDocument().addDocumentListener(dl);
        phoneNumTXT.getDocument().addDocumentListener(dl);
        emailTXT.getDocument().addDocumentListener(dl);
    }

    @SuppressWarnings("unchecked")
    public void setRowFilters() {
        rEmployeePNL.getEmpTBL().setRowFilters(
                rEmployeePNL.getEmpTBL().createTextFilter(idTXT, Employee.ID_NUM),
                rEmployeePNL.getEmpTBL().createTextFilter(nameTXT, Employee.NAME),
                rEmployeePNL.getEmpTBL().createTextFilter(addressTXT, Employee.ADDRESS),
                rEmployeePNL.getEmpTBL().createTextFilter(phoneNumTXT, Employee.PHONE_NUM),
                rEmployeePNL.getEmpTBL().createTextFilter(emailTXT, Employee.EMAIL),
                rEmployeePNL.getEmpTBL().createComboBoxFilter(typeCMB, Employee.TYPE),
                rEmployeePNL.getEmpTBL().createComboBoxFilter(depCMB, Employee.DEP_CODE)
        );
    }

    /**
     * Method clears text field
     */
    public void clear() {
        idTXT.setText("");
        nameTXT.setText("");
        addressTXT.setText("");
        phoneNumTXT.setText("");
        emailTXT.setText("");
        typeCMB.setSelectedItem(0);
        depCMB.setSelectedItem(0);

        print.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(typeCMB) || e.getSource().equals(depCMB)) {
            setRowFilters();
        } else if (e.getSource().equals(print)) {
            EntityDate date = EntityDate.today();
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(ServerApp.rPNL.getPnl(), "Employee Report_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = rEmployeePNL.getEmpTBL().getTbl().getSelectedRows();

            TableList<Employee, String> employees = new TableList<>(Employee.class, Employee.headers);
            List<TableList<Invoice, Integer>> custInvoices = new ArrayList<>();
            for (int row : rows) {
                Employee employee = ServerApp.employees.get(rEmployeePNL.getEmpTBL().convertRowIndexToModel(row));
                employees.add(employee);
                List<Invoice> temp = ServerApp.invoices.filter(inv -> inv.getEmployee().getIdNum().equals(employee.getIdNum()));
                custInvoices.add(temp.size() == 0 ? null : new TableList<>(Invoice.class, Invoice.headers).refresh(temp));
            }

            if (ReportGenerator.personReport(filePath, "Employee", employees.to2DArray(), employees.getHeaders(), custInvoices)) {
                JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Employee Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, ServerApp.rPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(refresh)) {
            ServerApp.refresh("Employee");
            JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Successfully Refreshed!", title, JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource().equals(clear)) {
            rEmployeePNL.clear();
            clear.setSelected(false);
        }
    }
}
