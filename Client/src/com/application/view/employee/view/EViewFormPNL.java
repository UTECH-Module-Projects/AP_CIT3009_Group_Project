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
package com.application.view.employee.view;

import com.application.models.misc.EntityDate;
import com.application.models.tables.Customer;
import com.application.models.tables.Employee;
import com.application.view.ClientApp;
import com.application.view.employee.EViewPNL;
import com.application.view.utilities.GUIDatePicker;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

@Getter
@Setter
public class EViewFormPNL implements ActionListener {
    private final String title;
    private final EViewPNL eViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, dobLBL, addressLBL, phoneNumLBL, emailLBL, typeLBL, depLBL, domeLBL;
    private JTextField idTXT, nameTXT, phoneNumTXT, emailTXT;
    private JTextArea addressTXTA;
    private GUIDatePicker dob;
    private JComboBox<String> typeCMB;
    private JComboBox<String> depCMB;
    public JButton save, delete, clear;
    private String empID;

    /**
     * Primary Constructor
     * @param title
     * @param eViewPNL
     * @param client
     */
    public EViewFormPNL(String title, EViewPNL eViewPNL, Client client) {
        this.title = title;
        this.eViewPNL = eViewPNL;
        this.client = client;
        this.empID = (String) client.genID("Employee", Employee.idLength);
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this form
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 0]10[]10[]10[]", "[]5[]5[]5[]5[]5[]5[]5[]15[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Full Name:");
        dobLBL = new JLabel("Date of Birth:");
        emailLBL = new JLabel("Email:");
        phoneNumLBL = new JLabel("Phone Number:");
        addressLBL = new JLabel("Address:");
        typeLBL = new JLabel("Type:");
        depLBL = new JLabel("Department");

        idTXT = new JTextField(empID);
        nameTXT = new JTextField();
        dob = new GUIDatePicker();
        emailTXT = new JTextField();
        phoneNumTXT = new JTextField();
        addressTXTA = new JTextArea(5, 30);
        typeCMB = new JComboBox<>(Employee.types);
        depCMB = new JComboBox<>(ClientApp.depNames);

        save = new JButton("Save", new ImageIcon(ClientApp.saveIMG));
        delete = new JButton("Delete", new ImageIcon(ClientApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ClientApp.clearIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL);
        pnl.add(idTXT, "growx, span 2, wrap");
        pnl.add(nameLBL);
        pnl.add(nameTXT, "growx, span 3, wrap");
        pnl.add(dobLBL);
        pnl.add(dob.getDate(), "growx, span 2, wrap");
        pnl.add(emailLBL);
        pnl.add(emailTXT, "growx, span 3, wrap");
        pnl.add(phoneNumLBL);
        pnl.add(phoneNumTXT, "growx, span 2, wrap");
        pnl.add(addressLBL);
        pnl.add(addressTXTA, "grow, span 3, wrap");
        pnl.add(typeLBL);
        pnl.add(typeCMB, "growx, span 2, wrap");
        pnl.add(depLBL);
        pnl.add(depCMB, "growx, span 2, wrap");

        pnl.add(save, "skip 1, width 100");
        pnl.add(delete, "width 100");
        pnl.add(clear, "wrap, width 100");
    }

    /**
     * Sets properties of components on the form
     */
    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Employee Form"));

        idTXT.setEnabled(false);

        delete.setEnabled(false);

        clear.addActionListener(this);
        save.addActionListener(this);
        delete.addActionListener(this);
    }

    /**
     * method clears form fields
     */
    public void clear() {

        EntityDate date = EntityDate.today();

        idTXT.setText(empID);
        nameTXT.setText("");
        dob.setDate(date);
        emailTXT.setText("");
        phoneNumTXT.setText("");
        addressTXTA.setText("");

        typeCMB.setSelectedIndex(0);
        depCMB.setSelectedIndex(0);

        delete.setEnabled(false);

        eViewPNL.getInvPNL().clear();
        eViewPNL.getEmpTBL().clearSelection();
        eViewPNL.setEmpIndex(-1);
        eViewPNL.getSearch().getPrint().setEnabled(false);
    }

    /**
     * Method updates specific employee info in table based on form input
     * @param employee
     */
    public void update(Employee employee) {
        delete.setEnabled(true);
        idTXT.setText(employee.getIdNum());
        nameTXT.setText(employee.getName());
        dob.setDate(employee.getDob());
        emailTXT.setText(employee.getEmail());
        phoneNumTXT.setText(employee.getPhoneNum());
        addressTXTA.setText(employee.getAddress());

        typeCMB.setSelectedItem(employee.getType());
        depCMB.setSelectedItem(employee.getDepartment().getName());
    }

    private void showMessageDialog(String msg, int type) {
        JOptionPane.showMessageDialog(eViewPNL.getEPNL().getPnl(), msg, title, type);
    }

    private int showConfirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(eViewPNL.getEPNL().getPnl(), msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int empIndex = eViewPNL.getEmpIndex();
        if (e.getSource().equals(clear)) {
            clear();
        } else if (e.getSource().equals(save) || (e.getSource().equals(delete) && empIndex != -1)) {
            boolean isSave = e.getSource().equals(save);
            boolean isNew = empID.equals(idTXT.getText());
            Employee employee = null;

            String btnType = (isSave ? "Save" : "Delete");
            String cudType = (isSave ? (isNew ? "create" : "update") : "delete");

            if (showConfirmDialog(btnType + " Employee?") == JOptionPane.YES_OPTION) {
                if (isSave) {
                    employee = new Employee(
                            idTXT.getText(),
                            nameTXT.getText(),
                            dob.toDate(),
                            addressTXTA.getText(),
                            phoneNumTXT.getText(),
                            emailTXT.getText(),
                            (String) typeCMB.getSelectedItem(),
                            ClientApp.departments.get(depCMB.getSelectedIndex())
                    );
                    try {
                        if (!employee.isValid()) {
                            client.log(Level.WARN, "Could not " + cudType + " employee! {invalid details}");
                            showMessageDialog("Invalid Employee Details! Try again...", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (ParseException ex) {
                        client.log(Level.WARN, "Unable to complete isValid check!", ex);
                        showMessageDialog("An unknown error occurred! Please contact admin for assistance...", JOptionPane.ERROR_MESSAGE);
                    }
                }

                boolean result = (Boolean) client.cud(cudType, "Employee", (isSave ? employee : ClientApp.employees.get(empIndex).getIdNum()));
                String idNum = (isSave ? employee.getIdNum() : ClientApp.employees.get(empIndex).getIdNum());

                if (result) {
                    client.log(Level.INFO, cudType + "d employee. {" + idNum + "}");
                    showMessageDialog("Employee successfully " + cudType + "!", JOptionPane.INFORMATION_MESSAGE);

                    if (isSave) {
                        if (isNew) {
                            empID = (String) client.genID("Employee", Customer.idLength);
                            ClientApp.employees.add(employee);
                            eViewPNL.getEmpTBL().getTModel().addRow(employee.toArray());
                        } else {
                            ClientApp.employees.set(empIndex, employee);
                            eViewPNL.getEmpTBL().updateRow(empIndex, employee.toArray());
                        }
                    } else {
                        ClientApp.employees.remove(empIndex);
                        eViewPNL.getEmpTBL().getTModel().removeRow(empIndex);
                    }

                    ClientApp.update("Employee");
                } else {
                    client.log(Level.WARN, "Could not " + cudType + " employee! {" + idNum + "}");
                    JOptionPane.showMessageDialog(pnl, "Could not " + cudType + " employee! Try again...", title, JOptionPane.ERROR_MESSAGE);

                    if (isNew) {
                        empID = (String) client.genID("Employee", Customer.idLength);
                        idTXT.setText(empID);
                    }
                }
            }
        }
    }
}
