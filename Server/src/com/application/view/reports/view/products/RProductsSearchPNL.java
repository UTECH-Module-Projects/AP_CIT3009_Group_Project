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

@Getter
public class RProductsSearchPNL implements ActionListener {
    private static final InvalidCharListener charListener = new InvalidCharListener(new char[]{'\\', '(', ')', '*', '.', '?', '|', '+', '$', '^'});

    private final String title;
    private final RProductsPNL rProductsPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, shDescLBL, loDescLBL, stockLBL, priceLBL;
    private JTextField idTXT, nameTXT, shDescTXT, loDescTXT, stockTXT, priceTXT;
    private JButton print, refresh, clear;

    /**
     * Primary Constructor
     * @param title
     * @param rProductsPNL
     * @param client
     */
    public RProductsSearchPNL(String title, RProductsPNL rProductsPNL, Client client) {
        this.title = title;
        this.rProductsPNL = rProductsPNL;
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
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10, gapx 10", "[grow 33][grow 33][grow 33]", "[][][][]10[]15[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Product Name:");
        shDescLBL = new JLabel("Short Description:");
        loDescLBL = new JLabel("Long Description:");
        stockLBL = new JLabel("Stock:");
        priceLBL = new JLabel("Price:");

        idTXT = new JTextField();
        nameTXT = new JTextField();
        shDescTXT = new JTextField();
        loDescTXT = new JTextField();
        stockTXT = new JTextField();
        priceTXT = new JTextField();

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
        pnl.add(shDescLBL, "grow, wrap");

        pnl.add(idTXT, "grow");
        pnl.add(nameTXT, "grow");
        pnl.add(shDescTXT, "grow, wrap");

        pnl.add(loDescLBL, "grow, span, wrap");

        pnl.add(loDescTXT, "grow, span, wrap");

        pnl.add(stockLBL, "span, split 4");
        pnl.add(stockTXT, "width 150, gapright 10");
        pnl.add(priceLBL);
        pnl.add(priceTXT, "width 150, wrap");

        pnl.add(print, "width 100, span, split 3, left, gapright 10");
        pnl.add(refresh, "width 100, left, gapright 10");
        pnl.add(clear, "width 100, left, wrap");
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
        pnl.setBorder(BorderFactory.createEtchedBorder());

        print.setEnabled(false);

        print.addActionListener(this);
        refresh.addActionListener(this);
        clear.addActionListener(this);


        idTXT.addKeyListener(charListener);
        nameTXT.addKeyListener(charListener);
        shDescTXT.addKeyListener(charListener);
        stockTXT.addKeyListener(specialCharListener);
        priceTXT.addKeyListener(specialCharListener);
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
        shDescTXT.getDocument().addDocumentListener(dl);
        loDescTXT.getDocument().addDocumentListener(dl);
        stockTXT.getDocument().addDocumentListener(dl);
        priceTXT.getDocument().addDocumentListener(dl);
    }

    @SuppressWarnings("unchecked")
    public void setRowFilters() {
         rProductsPNL.getProdTBL().setRowFilters(
                 rProductsPNL.getProdTBL().createTextFilter(idTXT, Product.ID_NUM),
                 rProductsPNL.getProdTBL().createTextFilter(nameTXT, Product.NAME),
                 rProductsPNL.getProdTBL().createTextFilter(shDescTXT, Product.SH_DESC),
                 rProductsPNL.getProdTBL().createTextFilter(loDescTXT, Product.LO_DESC),
                 rProductsPNL.getProdTBL().createTextFilter(stockTXT, Product.STOCK),
                 rProductsPNL.getProdTBL().createTextFilter(priceTXT, Product.PRICE)
        );
    }

    /**
     * Method clears text field
     */
    public void clear() {
        idTXT.setText("");
        nameTXT.setText("");
        shDescTXT.setText("");
        loDescTXT.setText("");
        stockTXT.setText("");
        priceTXT.setText("");

        print.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(print)) {
            EntityDate date = EntityDate.today();
            FileLocationSelector selector = new FileLocationSelector();
            String filePath = selector.getFilePath(rProductsPNL.getPnl(), "Product Report_" + date + ".pdf");
            if (filePath == null) return;

            int[] rows = rProductsPNL.getProdTBL().getSelectedRows();

            TableList<Product, String> products = new TableList<>(Product.class, Product.headers);
            for (int row : rows) {
                Product product = ServerApp.products.get(rProductsPNL.getProdTBL().convertRowIndexToModel(row));
                products.add(product);
            }

            if (ReportGenerator.productsReport(filePath, products)) {
                JOptionPane.showMessageDialog(rProductsPNL.getPnl(), "Product Report successfully generated!", title, JOptionPane.INFORMATION_MESSAGE);

                if (!Desktop.isDesktopSupported()) {
                    JOptionPane.showMessageDialog(rProductsPNL.getPnl(), "Error when opening document!", title, JOptionPane.ERROR_MESSAGE);
                } else selector.openFile(filePath, rProductsPNL.getPnl(), title);
            } else {
                JOptionPane.showMessageDialog(rProductsPNL.getPnl(), "Error when creating document!", title, JOptionPane.ERROR_MESSAGE);
            }
            print.setSelected(false);
        } else if (e.getSource().equals(refresh)) {
            ServerApp.refresh("Product");
            JOptionPane.showMessageDialog(ServerApp.rPNL.getPnl(),"Successfully Refreshed!", title, JOptionPane.INFORMATION_MESSAGE);
            refresh.setSelected(false);
        } else if (e.getSource().equals(clear)) {
            rProductsPNL.clear();
            clear.setSelected(false);
        }
    }
}
