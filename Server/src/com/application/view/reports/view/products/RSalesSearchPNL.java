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
package com.application.view.reports.view.products;

import com.application.generic.TableList;
import com.application.models.misc.EntityDate;
import com.application.models.tables.Product;
import com.application.view.ServerApp;
import com.application.view.inventory.INVViewPNL;
import com.application.view.reports.view.RProductsPNL;
import com.application.view.utilities.FileLocationSelector;
import com.application.view.utilities.GUIDatePicker;
import com.application.view.utilities.InvalidCharListener;
import com.application.view.utilities.ReportGenerator;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class RSalesSearchPNL implements ActionListener {
    private static final InvalidCharListener charListener = new InvalidCharListener(new char[]{'\\', '(', ')', '*', '.', '?', '|', '+', '$', '^'});

    private final String title;
    private final RProductsPNL rProductsPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel dateStartLBL, dateEndLBL;
    private GUIDatePicker dateStart, dateEnd;
    private JButton print;

    /**
     * Primary Constructor
     * @param title
     * @param rProductsPNL
     * @param client
     */
    public RSalesSearchPNL(String title, RProductsPNL rProductsPNL, Client client) {
        this.title = title;
        this.rProductsPNL = rProductsPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this search panel
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowx, ins 10, gapx 10", "[nogrid]", "[nogrid][nogrid]20[]"));

        dateStartLBL = new JLabel("Start:");
        dateEndLBL = new JLabel("End:");

        dateStart = new GUIDatePicker();
        dateEnd = new GUIDatePicker();

        print = new JButton("Sales Report", new ImageIcon(ServerApp.repIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(dateStartLBL, "grow 0");
        pnl.add(dateStart.getDate(), "wrap");

        pnl.add(dateEndLBL, "grow 0");
        pnl.add(dateEnd.getDate(), "wrap");

        pnl.add(print, "width 100, center");
    }

    /**
     * Sets properties of components
     */
    private void setProperties() {
        pnl.setBorder(BorderFactory.createEtchedBorder());
        print.addActionListener(this);
    }

    /**
     * Method clears text field
     */
    public void clear() {
        EntityDate today = EntityDate.today();
        dateStart.setDate(today);
        dateEnd.setDate(today);

        print.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(print)) {
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(ServerApp.rPNL.getPnl(), "Sales Report_" + dateStart + "-" + dateEnd + ".pdf");
            if (filePath == null) return;

            String[] headers = {"Product", "Stock", "Cost Price", "Total Sold", "Total Sales"};
            String[][] data = new String[ServerApp.products.size()][headers.length];
            String[] summary = new String[headers.length];
            summary[0] = "";
            summary[1] = "";
            summary[2] = "Total";
            int totOverallSold = 0;
            double totOverallSales = 0;
            double[] prodSales = new double[ServerApp.products.size()];
            for (int i = 0; i < ServerApp.products.size(); i++) {
                Product prod = ServerApp.products.get(i);

                int prodSold = ServerApp.invoices.reduce(0, (totSold, invoice) -> ((invoice.getBillDate().compare(dateStart.toDate()) == 1) || (invoice.getBillDate().compare(dateStart.toDate()) == 0) && (invoice.getBillDate().compare(dateEnd.toDate()) == -1) || (invoice.getBillDate().compare(dateEnd.toDate()) == 0)) ? (invoice.getItems().stream().reduce(0, (tot, item) -> (item.getProduct() != null ? (item.getProduct().getIdNum().equals(prod.getIdNum()) ? item.getQuantity() : 0) : 0), Integer::sum)) : 0, Integer::sum);

                prodSales[i] = prod.getPrice() * prodSold;

                data[i] = new String[]{prod.getName(), String.valueOf(prod.getStock()), String.format("$%.2f", prod.getPrice()), String.valueOf(prodSold),  String.format("$%.2f", prodSales[i])};

                totOverallSold += prodSold;
                totOverallSales += prodSales[i];
            }
            data = Arrays.stream(data).sorted((row1, row2) -> row2[4].compareTo(row1[4])).toList().toArray(String[][]::new);

            summary[3] = String.valueOf(totOverallSold);
            summary[4] = String.format("$%.2f", totOverallSales);

            if (ReportGenerator.salesReport(filePath, dateStart + " to " + dateEnd, data, summary, headers)) {
                JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Sales Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, ServerApp.rPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            print.setSelected(false);
        }
    }
}
