package com.application.view.customer.view;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Customer;
import com.application.models.tables.Invoice;
import com.application.view.ServerApp;
import com.application.view.customer.CViewPNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.InvalidCharListener;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

@Getter
public class CViewSearchPNL {
    private final CViewPNL cViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, addressLBL, phoneNumLBL, emailLBL;
    private JTextField idTXT, nameTXT, addressTXT, phoneNumTXT, emailTXT;
    private JButton print, refresh, clear;

    /**
     * Primary Constructor
     * @param title
     * @param cViewPNL
     * @param client
     */
    public CViewSearchPNL(String title, CViewPNL cViewPNL, Client client) {
        this.title = title;
        this.cViewPNL = cViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10, gapx 10", "[grow 10][grow 50][grow 40]", "[][][][]15[]"));

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

        print = new JButton("Print", new ImageIcon(ServerApp.printIMG));
        refresh = new JButton("Refresh", new ImageIcon(ServerApp.refIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
    }

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

        pnl.add(print, "width 100, span, split 3, left, gapright 10");
        pnl.add(refresh, "width 100, left, gapright 10");
        pnl.add(clear, "width 100, left, wrap");
    }

    private void setProperties() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
        pnl.setBorder(titledBorder);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(print)) {
            EntityDate date = EntityDate.today();
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(cViewPNL.getPnl(), "Customer Report_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = cViewPNL.getCustTBL().getTbl().getSelectedRows();

            TableList<Customer, String> customers = new TableList<>(Customer.class, Customer.headers);
            List<TableList<Invoice, Integer>> custInvoices = new ArrayList<>();
            for (int row : rows) {
                Customer customer = ServerApp.customers.get(cViewPNL.getCustTBL().convertRowIndexToModel(row));
                customers.add(customer);
                List<Invoice> temp = ServerApp.invoices.filter(inv -> inv.getCustomer().getIdNum().equals(customer.getIdNum()));
                custInvoices.add(temp.size() == 0 ? null : new TableList<>(Invoice.class, Invoice.headers).refresh(temp));
            }

            if (ReportGenerator.personReport(filePath, "Customer", customers.to2DArray(), customers.getHeaders(), custInvoices)) {
                JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Customer Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(cViewPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            selector.openFile(filePath, cViewPNL.getPnl(), title);
        } else if (e.getSource().equals(refresh)) {
            cViewPNL.refresh();
            ServerApp.invoices.refresh(client.getAll("Invoice"));
        } else if (e.getSource().equals(clear)) {
            clear();
        }
    }
}
