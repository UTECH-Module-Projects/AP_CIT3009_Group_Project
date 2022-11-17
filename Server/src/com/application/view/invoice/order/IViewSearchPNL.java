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
package com.application.view.invoice.order;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.*;
import com.application.view.ServerApp;
import com.application.view.invoice.IViewPNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.InvalidCharListener;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class IViewSearchPNL implements ActionListener {
    private static final InvalidCharListener charListener = new InvalidCharListener(new char[]{'\\', '(', ')', '*', '.', '?', '|', '+', '$', '^'});

    private final String title;
    private final IViewPNL iViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, dateLBL, custLBL, empLBL, itemsLBL, totLBL;
    private JTextField idTXT, dateTXT, itemsTXT, totTXT;
    private JComboBox<String> custCMB, empCMB;
    private JButton print, refresh, clear, delete;

    /**
     * Primary Constructor
     * @param title
     * @param iViewPNL
     * @param client
     */
    public IViewSearchPNL(String title, IViewPNL iViewPNL, Client client) {
        this.title = title;
        this.iViewPNL = iViewPNL;
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
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10, gapx 10", "[grow 33][grow 33][grow 34]", "[][][][]15[]"));

        idLBL = new JLabel("ID Number:");
        dateLBL = new JLabel("Date:");

        empLBL = new JLabel("Employee:");
        itemsLBL = new JLabel("Items:");
        totLBL = new JLabel("Total:");

        custLBL = new JLabel("Customer:");

        idTXT = new JTextField();
        dateTXT = new JTextField();
        itemsTXT = new JTextField();
        totTXT = new JTextField();

        custCMB = new JComboBox<>();
        empCMB = new JComboBox<>();

        print = new JButton("Print", new ImageIcon(ServerApp.printIMG));
        refresh = new JButton("Refresh", new ImageIcon(ServerApp.refIMG));
        clear = new JButton("Clear", new ImageIcon(ServerApp.clearIMG));
        delete = new JButton("Delete", new ImageIcon(ServerApp.deleteIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL, "grow");
        pnl.add(dateLBL, "grow");
        pnl.add(custLBL, "grow, wrap");

        pnl.add(idTXT, "grow");
        pnl.add(dateTXT, "grow");
        pnl.add(custCMB, "grow, wrap");

        pnl.add(itemsLBL, "grow");
        pnl.add(totLBL, "grow");
        pnl.add(empLBL, "grow, wrap");

        pnl.add(itemsTXT, "grow");
        pnl.add(totTXT, "grow");
        pnl.add(empCMB, "grow, wrap");

        pnl.add(print, "width 100, span, split 4, left, gapright 10");
        pnl.add(refresh, "width 100, left, gapright 10");
        pnl.add(clear, "width 100, left, gapright 10");
        pnl.add(delete, "width 100, left, wrap");
    }

    /**
     * Sets properties of components
     */
    private void setProperties() {
        InvalidCharListener specialCharListener = new InvalidCharListener(new char[]{
                '\\', '(', ')', '*', '.', '?', '|', '+', '$', '^',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '=', '-', '_', '~', '`'
        });
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search");
        pnl.setBorder(titledBorder);

        print.setEnabled(false);
        delete.setEnabled(false);

        refresh();

        custCMB.addActionListener(this);
        empCMB.addActionListener(this);

        print.addActionListener(this);
        refresh.addActionListener(this);
        clear.addActionListener(this);
        delete.addActionListener(this);

        idTXT.addKeyListener(charListener);
        dateTXT.addKeyListener(charListener);
        itemsTXT.addKeyListener(specialCharListener);
        totTXT.addKeyListener(specialCharListener);
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
        dateTXT.getDocument().addDocumentListener(dl);
        itemsTXT.getDocument().addDocumentListener(dl);
        totTXT.getDocument().addDocumentListener(dl);
    }

    @SuppressWarnings("unchecked")
    public void setRowFilters() {
        iViewPNL.getInvTBL().setRowFilters(
                iViewPNL.getInvTBL().createTextFilter(idTXT, Invoice.ID_NUM),
                iViewPNL.getInvTBL().createTextFilter(dateTXT, Invoice.BILL_DATE),
                iViewPNL.getInvTBL().createTextFilter(itemsTXT, Invoice.ITEMS),
                iViewPNL.getInvTBL().createTextFilter(totTXT, Invoice.TOTAL),
                iViewPNL.getInvTBL().createComboBoxFilter(custCMB, Invoice.CUSTOMER),
                iViewPNL.getInvTBL().createComboBoxFilter(empCMB, Invoice.EMPLOYEE)
        );
    }

    /**
     * Method clears text field
     */
    public void clear() {
        idTXT.setText("");
        dateTXT.setText("");
        itemsTXT.setText("");
        totTXT.setText("");

        empCMB.setSelectedIndex(0);
        custCMB.setSelectedIndex(0);

        print.setEnabled(false);
        delete.setEnabled(false);
    }

    public void refresh() {
        custCMB.setModel(new JComboBox<>(ServerApp.customers.map(Customer::getName).toArray(new String[0])).getModel());
        custCMB.insertItemAt("", 0);
        custCMB.setSelectedIndex(0);

        empCMB.setModel(new JComboBox<>(ServerApp.employees.map(Employee::getName).toArray(new String[0])).getModel());
        empCMB.insertItemAt("", 0);
        empCMB.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(custCMB) || e.getSource().equals(empCMB)) {
            setRowFilters();
        }else if (e.getSource().equals(print)) {
            EntityDate date = EntityDate.today();
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(iViewPNL.getPnl(), "Invoice Report_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = iViewPNL.getInvTBL().getSelectedRows();

            TableList<Invoice, Integer> invoices = new TableList<>(Invoice.class, Invoice.headers);
            for (int row : rows) {
                Invoice invoice = ServerApp.invoices.get(iViewPNL.getInvTBL().convertRowIndexToModel(row));
                invoices.add(invoice);
            }

            if (ReportGenerator.invoiceReport(filePath, invoices, InvoiceItem.headers)) {
                JOptionPane.showMessageDialog(iViewPNL.getPnl(), "Invoice Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(iViewPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, iViewPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(iViewPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            iViewPNL.clear();
            print.setSelected(false);
        } else if (e.getSource().equals(delete)) {
            int[] rows = iViewPNL.getInvTBL().getSelectedRows();
            int totRows = rows.length;

            String resultS = totRows > 1 ? "s" : "";

            if (JOptionPane.showConfirmDialog(iViewPNL.getPnl(), "Delete Invoice" + resultS + "?", title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int count = 0;
                for (int row : rows) {
                    Invoice invoice = ServerApp.invoices.get(iViewPNL.getInvTBL().convertRowIndexToModel(row));

                    boolean result = (boolean) client.cud("delete", "Invoice", invoice.getIdNum());

                    if (result) {
                        client.log(Level.INFO, "deleted invoice. {" + invoice.getIdNum() + "}");
                        ++count;
                    } else {
                        client.log(Level.WARN, "could not delete invoice! {" + invoice.getIdNum() + "}");
                    }
                }
                JOptionPane.showMessageDialog(iViewPNL.getPnl(), "(" + count + ") Invoice" + resultS + " successfully deleted!", title, JOptionPane.INFORMATION_MESSAGE);
                ServerApp.update("Invoice");
                delete.setSelected(false);
            }
        } else if (e.getSource().equals(refresh)) {
            ServerApp.refresh("Invoice");
            JOptionPane.showMessageDialog(iViewPNL.getPnl(), "Successfully Refreshed!", title, JOptionPane.INFORMATION_MESSAGE);
            refresh.setSelected(false);
        } else if (e.getSource().equals(clear)) {
            iViewPNL.clear();
            clear.setSelected(false);
        }
    }
}
