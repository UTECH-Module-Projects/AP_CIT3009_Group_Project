package com.application.view.customer.view;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Customer;
import com.application.view.ServerApp;
import com.application.view.customer.CViewPNL;
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
public class CViewFormPNL implements ActionListener {
    private final String title;
    private final CViewPNL cViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, dobLBL, addressLBL, phoneNumLBL, emailLBL, memLBL, domLBL, domeLBL;
    private JTextField idTXT, nameTXT, phoneNumTXT, emailTXT;
    private JTextArea addressTXTA;
    public CViewFormMemBTN isMem;
    private GUIDatePicker dob, dom, dome;
    public JButton save, delete, clear;
    private String custID;

    /**
     * Primary Constructor
     * @param title
     * @param cViewPNL
     * @param client
     */
    public CViewFormPNL(String title, CViewPNL cViewPNL, Client client) {
        this.title = title;
        this.cViewPNL = cViewPNL;
        this.client = client;
        this.custID = (String) client.genID("Customer", Customer.idLength);
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 0]10[]10[]10[]", "[]5[]5[]5[]5[]5[]5[]5[]5[]15[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Full Name:");
        dobLBL = new JLabel("Date of Birth:");
        emailLBL = new JLabel("Email:");
        phoneNumLBL = new JLabel("Phone Number:");
        addressLBL = new JLabel("Address:");
        memLBL = new JLabel("Is a Member?");
        domLBL = new JLabel("Date of Membership:");
        domeLBL = new JLabel("Date of Expiry:");

        idTXT = new JTextField(custID);
        nameTXT = new JTextField();
        dob = new GUIDatePicker();
        emailTXT = new JTextField();
        phoneNumTXT = new JTextField();
        addressTXTA = new JTextArea(5, 30);
        isMem = new CViewFormMemBTN(this);
        dom = new GUIDatePicker();
        dome = new GUIDatePicker();

        save = new JButton("Save", new ImageIcon(ServerApp.saveIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
    }

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
        pnl.add(memLBL);
        pnl.add(isMem.getYes());
        pnl.add(isMem.getNo(), "growx, wrap");
        pnl.add(domLBL);
        pnl.add(dom.getDate(), "growx, span 2, wrap");
        pnl.add(domeLBL);
        pnl.add(dome.getDate(), "growx, span 2, wrap");

        pnl.add(save, "center, span, split 3, width 100");
        pnl.add(delete, "center, width 100");
        pnl.add(clear, "center, wrap, width 100, wrap");
    }

    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Customer Form"));

        idTXT.setEnabled(false);

        domLBL.setVisible(false);
        domeLBL.setVisible(false);

        dom.setVisible(false);
        dome.setVisible(false);

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
        
        idTXT.setText(custID);
        nameTXT.setText("");
        dob.setDate(date);
        emailTXT.setText("");
        phoneNumTXT.setText("");
        addressTXTA.setText("");
        isMem.getNo().setSelected(true);
        dom.setDate(date);
        dome.setDate(date);
        delete.setEnabled(false);

        cViewPNL.getInvPNL().clear();
        cViewPNL.getCustTBL().clearSelection();
        cViewPNL.setCustIndex(-1);
        cViewPNL.getSearch().getPrint().setEnabled(false);
    }

    /**
     * Method updates specific customer info in table based on form input
     * @param customer
     */
    public void update(Customer customer) {
        delete.setEnabled(true);
        idTXT.setText(customer.getIdNum());
        nameTXT.setText(customer.getName());
        dob.setDate(customer.getDob());
        emailTXT.setText(customer.getEmail());
        phoneNumTXT.setText(customer.getPhoneNum());
        addressTXTA.setText(customer.getAddress());

        if (customer.isMem()) {
            isMem.getYes().setSelected(true);
            if (customer.getDom() != null) {
                dom.setDate(customer.getDom());
                dome.setDate(customer.getDome());
            }
        } else isMem.getNo().setSelected(true);
    }

    private void showMessageDialog(String msg, int type) {
        JOptionPane.showMessageDialog(cViewPNL.getCPNL().getPnl(), msg, title, type);
    }

    private int showConfirmDialog(String msg) {
        return JOptionPane.showConfirmDialog(cViewPNL.getCPNL().getPnl(), msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int custIndex = cViewPNL.getCustIndex();
        if (e.getSource().equals(clear)) {
            clear();
            clear.setSelected(false);
        } else if (e.getSource().equals(save) || (e.getSource().equals(delete) && custIndex != -1)) {
            boolean isSave = e.getSource().equals(save);
            boolean isNew = custID.equals(idTXT.getText());
            Customer customer = null;

            String btnType = (isSave ? "Save" : "Delete");
            String cudType = (isSave ? (isNew ? "create" : "update") : "delete");

            if (showConfirmDialog(btnType + " Customer?") == JOptionPane.YES_OPTION) {
                if (isSave) {
                    boolean isMemSelected = isMem.getYes().isSelected();
                    customer = new Customer(
                            idTXT.getText(),
                            nameTXT.getText(),
                            dob.toDate(),
                            addressTXTA.getText(),
                            phoneNumTXT.getText(),
                            emailTXT.getText(),
                            isMemSelected,
                            isMemSelected ? dom.toDate() : null,
                            isMemSelected ? dome.toDate() : null
                    );
                    try {
                        if (!customer.isValid()) {
                            client.log(Level.WARN, "Could not " + cudType + " customer! {invalid details}");
                            showMessageDialog("Invalid Customer Details! Try again...", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (ParseException ex) {
                        client.log(Level.WARN, "Unable to complete isValid check!", ex);
                        showMessageDialog("An unknown error occurred! Please contact admin for assistance...", JOptionPane.ERROR_MESSAGE);
                    }
                }

                boolean result = (Boolean) client.cud(cudType, "Customer", (isSave ? customer : ServerApp.customers.get(custIndex).getIdNum()));
                String idNum = (isSave ? customer.getIdNum() : ServerApp.customers.get(custIndex).getIdNum());

                if (result) {
                    client.log(Level.INFO, cudType + "d customer. {" + idNum + "}");
                    showMessageDialog("Customer successfully " + cudType + "!", JOptionPane.INFORMATION_MESSAGE);

                    if (isSave) {
                        if (isNew) {
                            custID = (String) client.genID("Customer", Customer.idLength);
                            ServerApp.customers.add(customer);
                            cViewPNL.getCustTBL().getTModel().addRow(customer.toArray());
                        } else {
                            ServerApp.customers.set(custIndex, customer);
                            cViewPNL.getCustTBL().updateRow(custIndex, customer.toArray());
                        }
                    } else {
                        ServerApp.customers.remove(custIndex);
                        cViewPNL.getCustTBL().getTModel().removeRow(custIndex);
                    }
                    ServerApp.update("Customer");
                } else {
                    client.log(Level.WARN, "Could not " + cudType + " customer! {" + idNum + "}");
                    JOptionPane.showMessageDialog(pnl, "Could not " + cudType + " customer! Try again...", title, JOptionPane.ERROR_MESSAGE);

                    if (isNew) {
                        custID = (String) client.genID("Customer", Customer.idLength);
                        idTXT.setText(custID);
                    }
                }
            }
            save.setSelected(false);
            delete.setSelected(false);
        }
    }
}
