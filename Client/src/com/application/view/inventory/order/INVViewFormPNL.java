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
package com.application.view.inventory.order;

import com.application.models.tables.Product;
import com.application.view.ClientApp;
import com.application.view.inventory.INVViewPNL;
import com.application.view.utilities.InvalidCharListener;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class INVViewFormPNL implements ActionListener {
    private final String title;
    private final INVViewPNL invViewPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, shDescLBL, loDescLBL, stockLBL, totSoldLBL, priceLBL;
    private JTextField idTXT, nameTXT, shDescTXT, stockTXT, totSoldTXT, priceTXT;
    private JTextArea loDescTXTA;
    public JButton save, delete, clear;
    private String prodID;

    public INVViewFormPNL(String title, INVViewPNL invViewPNL, Client client) {
        this.title = title;
        this.invViewPNL = invViewPNL;
        this.client = client;
        this.prodID = (String) client.genID("Product", Product.idLength);
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this form
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[grow 0]10[grow 50][grow 50]", "[]5[]5[]5[]5[]5[]5[]15[]"));

        idLBL = new JLabel("ID Number:");
        nameLBL = new JLabel("Product Name:");
        shDescLBL = new JLabel("Short Description:");
        loDescLBL = new JLabel("Long Description:");
        stockLBL = new JLabel("Stock:");
        totSoldLBL = new JLabel("Total Sold:");
        priceLBL = new JLabel("Unit Price:");

        idTXT = new JTextField(prodID);
        nameTXT = new JTextField("");
        shDescTXT = new JTextField("");
        loDescTXTA = new JTextArea(5, 30);
        stockTXT = new JTextField("");
        totSoldTXT = new JTextField("0");
        priceTXT = new JTextField("");

        save = new JButton("Save", new ImageIcon(ClientApp.saveIMG));
        delete = new JButton("Delete", new ImageIcon(ClientApp.deleteIMG));
        clear = new JButton("Clear", new ImageIcon(ClientApp.clearIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL);
        pnl.add(idTXT, "growx, wrap");

        pnl.add(nameLBL);
        pnl.add(nameTXT, "growx, span, wrap");

        pnl.add(shDescLBL);
        pnl.add(shDescTXT, "growx, span, wrap");

        pnl.add(loDescLBL);
        pnl.add(loDescTXTA, "grow, span, wrap");

        pnl.add(stockLBL);
        pnl.add(stockTXT, "growx, wrap");

        pnl.add(totSoldLBL);
        pnl.add(totSoldTXT, "growx, wrap");

        pnl.add(priceLBL);
        pnl.add(priceTXT, "growx, wrap");

        pnl.add(save, "center, span, split 3, width 100");
        pnl.add(delete, "center, width 100");
        pnl.add(clear, "center, wrap, width 100, wrap");
    }

    /**
     * Sets properties of components on the form
     */
    private void setProperties() {
        InvalidCharListener stockListener = new InvalidCharListener(new char[]{
                '\\', '(', ')', '*', '.', '?', '|', '+', '$', '^',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '=', '-', '_', '~', '`', '/', '\t', '\n', '<', '>', ',', '&', '#', '@', '!'
        });

        InvalidCharListener priceListener = new InvalidCharListener(new char[]{
                '\\', '(', ')', '*', '?', '|', '+', '$', '^',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '=', '-', '_', '~', '`', '/', '\t', '\n', '<', '>', ',', '&', '#', '@', '!'
        });

        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Product Form"));

        idTXT.setEnabled(false);
        totSoldTXT.setEnabled(false);

        delete.setEnabled(false);

        stockTXT.addKeyListener(stockListener);
        priceTXT.addKeyListener(priceListener);

        clear.addActionListener(this);
        save.addActionListener(this);
        delete.addActionListener(this);
    }

    /**
     * method clears form fields
     */
    public void clear() {
        idTXT.setText(prodID);
        nameTXT.setText("");
        shDescTXT.setText("");
        loDescTXTA.setText("");
        stockTXT.setText("");
        totSoldTXT.setText("0");
        priceTXT.setText("");

        delete.setEnabled(false);

        invViewPNL.getProdTBL().clearSelection();
        invViewPNL.setProdIndex(-1);
        invViewPNL.getSearch().getPrint().setEnabled(false);
    }

    /**
     * Method updates specific product info in table based on form input
     *
     * @param product
     */
    public void update(Product product) {
        delete.setEnabled(true);

        idTXT.setText(product.getIdNum());
        nameTXT.setText(product.getName());
        shDescTXT.setText(product.getShDesc());
        loDescTXTA.setText(product.getLoDesc());
        stockTXT.setText(String.valueOf(product.getStock()));
        totSoldTXT.setText(String.valueOf(product.getTotSold()));
        priceTXT.setText(String.format("%.2f", product.getPrice()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int prodIndex = invViewPNL.getProdIndex();
        if (e.getSource().equals(clear)) {
            clear();
            clear.setSelected(false);
        } else if (e.getSource().equals(save) || (e.getSource().equals(delete) && prodIndex != -1)) {
            boolean isSave = e.getSource().equals(save);
            boolean isNew = prodID.equals(idTXT.getText());
            Product product = null;

            String btnType = (isSave ? "Save" : "Delete");
            String cudType = (isSave ? (isNew ? "create" : "update") : "delete");

            if (JOptionPane.showConfirmDialog(invViewPNL.getPnl(), btnType + " Product?", title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                if (isSave) {
                    product = new Product(
                            idTXT.getText(),
                            nameTXT.getText(),
                            shDescTXT.getText(),
                            loDescTXTA.getText(),
                            stockTXT.getText().equals("") ? -1 : Integer.parseInt(stockTXT.getText()),
                            totSoldTXT.getText().equals("") ? -1 : Integer.parseInt(totSoldTXT.getText()),
                            priceTXT.getText().equals("") ? -1 : Double.parseDouble(priceTXT.getText())
                    );
                    if (!product.isValid()) {
                        client.log(Level.WARN, "Could not " + cudType + " product! {invalid details}");
                        JOptionPane.showMessageDialog(invViewPNL.getPnl(), "Invalid Product Details! Try again...", title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                boolean result = (Boolean) client.cud(cudType, "Product", (isSave ? product : ClientApp.products.get(prodIndex).getIdNum()));
                String idNum = (isSave ? product.getIdNum() : ClientApp.products.get(prodIndex).getIdNum());

                if (result) {
                    client.log(Level.INFO, cudType + "d product. {" + idNum + "}");
                    JOptionPane.showMessageDialog(invViewPNL.getPnl(), "Product successfully " + cudType + "!", title, JOptionPane.INFORMATION_MESSAGE);

                    if (isSave) {
                        if (isNew) {
                            prodID = (String) client.genID("Product", Product.idLength);
                            ClientApp.products.add(product);
                            invViewPNL.getProdTBL().getTModel().addRow(product.toArray());
                        } else {
                            ClientApp.products.set(prodIndex, product);
                            invViewPNL.getProdTBL().updateRow(prodIndex, product.toArray());
                        }
                    } else {
                        ClientApp.products.remove(prodIndex);
                        invViewPNL.getProdTBL().getTModel().removeRow(prodIndex);
                    }
                    ClientApp.sPNL.getOrder().getProdTBL().refresh(ClientApp.products.to2DArray());
                    invViewPNL.clear();
                } else {
                    client.log(Level.WARN, "Could not " + cudType + " product! {" + idNum + "}");
                    JOptionPane.showMessageDialog(pnl, "Could not " + cudType + " product! Try again...", title, JOptionPane.ERROR_MESSAGE);

                    if (isNew) {
                        prodID = (String) client.genID("Product", Product.idLength);
                        idTXT.setText(prodID);
                    }
                }
            }
            save.setSelected(false);
            delete.setSelected(false);
        }
    }
}
