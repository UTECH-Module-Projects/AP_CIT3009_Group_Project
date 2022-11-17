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
package com.application.view.sales.order;

import com.application.generic.TableList;
import com.application.models.tables.*;
import com.application.view.ServerApp;
import com.application.view.sales.SOrderPNL;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;

@Getter
@Setter
public class SOrderFormPNL implements ActionListener {
    private final String title;
    private final SOrderPNL sOrderPNL;
    private final Client client;
    private final Invoice invoice;
    private final TableList<InvoiceItem, String> items;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, shDescLBL, loDescLBL, stockLBL, priceLBL, quantityLBL, totalLBL;
    private JTextField idTXT, nameTXT, shDescTXT, stockTXT, priceTXT, totalTXT;
    private JTextArea loDescTXTA;
    private JComboBox<String> quantity;
    public JButton add, cancel;
    private Product product;
    private String invItemID;


    /**
     * Primary Constructor
     *
     * @param title
     * @param sOrderPNL
     * @param client
     */
    public SOrderFormPNL(String title, SOrderPNL sOrderPNL, Client client, Invoice invoice, TableList<InvoiceItem, String> items) {
        this.title = title;
        this.sOrderPNL = sOrderPNL;
        this.client = client;
        this.invoice = invoice;
        this.items = items;
        invItemID = (String) client.genID("InvoiceItem", InvoiceItem.idLength);
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
        nameLBL = new JLabel("Product Name:");
        shDescLBL = new JLabel("Short Description:");
        loDescLBL = new JLabel("Long Description:");
        stockLBL = new JLabel("Stock:");
        priceLBL = new JLabel("Unit Price:");
        quantityLBL = new JLabel("Quantity:");
        totalLBL = new JLabel("Total Cost:");

        idTXT = new JTextField();
        nameTXT = new JTextField();
        shDescTXT = new JTextField();
        loDescTXTA = new JTextArea();
        stockTXT = new JTextField();
        priceTXT = new JTextField();
        totalTXT = new JTextField("$0.00");

        quantity = new JComboBox<>();

        add = new JButton("Add to Cart", new ImageIcon(ServerApp.salesIMG));
        cancel = new JButton("Cancel", new ImageIcon(ServerApp.cancelIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL);
        pnl.add(idTXT, "growx, span 2, wrap");

        pnl.add(nameLBL);
        pnl.add(nameTXT, "growx, span 3, wrap");

        pnl.add(shDescLBL);
        pnl.add(shDescTXT, "growx, span 3, wrap");

        pnl.add(loDescLBL);
        pnl.add(loDescTXTA, "grow, span 3, wrap");

        pnl.add(stockLBL);
        pnl.add(stockTXT, "growx, span 2, wrap");

        pnl.add(priceLBL);
        pnl.add(priceTXT, "growx, span 2, wrap");

        pnl.add(quantityLBL);
        pnl.add(quantity, "growx, wrap");

        pnl.add(totalLBL);
        pnl.add(totalTXT, "growx, span 2, wrap");

        pnl.add(add, "span, split, width 100, center");
        pnl.add(cancel, "width 100, center, wrap");
    }

    /**
     * Sets properties of components on the form
     */
    private void setProperties() {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Order Form"));
        pnl.setVisible(false);

        idTXT.setEditable(false);
        nameTXT.setEditable(false);
        shDescTXT.setEditable(false);
        loDescTXTA.setEditable(false);
        stockTXT.setEditable(false);
        priceTXT.setEditable(false);
        totalTXT.setEditable(false);

        totalTXT.setFont(new Font(totalTXT.getFont().getFontName(), Font.BOLD, 15));
        totalTXT.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        quantity.setEnabled(false);

        add.setEnabled(false);
        cancel.setEnabled(false);

        quantity.addActionListener(this);
        cancel.addActionListener(this);
        add.addActionListener(this);
    }

    /**
     * method clears form fields
     */
    public void clear() {
        idTXT.setText("");
        nameTXT.setText("");
        shDescTXT.setText("");
        loDescTXTA.setText("");
        stockTXT.setText("");
        priceTXT.setText("");
        totalTXT.setText("$0.00");

        quantity.setSelectedIndex(-1);

        add.setEnabled(false);
        cancel.setEnabled(false);

        sOrderPNL.getProdTBL().clearSelection();
        sOrderPNL.setProdIndex(-1);
    }

    /**
     * Method updates specific product info in table based on form input
     *
     * @param product
     */
    public void update(Product product) {
        String price = String.format("$%.2f", product.getPrice());
        idTXT.setText(product.getIdNum());
        nameTXT.setText(product.getName());
        shDescTXT.setText(product.getShDesc());
        loDescTXTA.setText(product.getLoDesc());
        stockTXT.setText(String.valueOf(product.getStock()));
        priceTXT.setText(price);
        totalTXT.setText(price);

        quantity.setEnabled(true);

        this.product = product;

        quantity.removeAllItems();
        quantity.setModel(new DefaultComboBoxModel<>(IntStream.range(1, product.getStock() + 1).mapToObj(String::valueOf).toArray(String[]::new)));
        quantity.setSelectedIndex(0);

        add.setEnabled(true);
        cancel.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancel)) {
            clear();
            cancel.setSelected(false);
        } else if (e.getSource().equals(quantity) || e.getSource().equals(add)) {
            int prodIndex = sOrderPNL.getProdIndex();
            if (prodIndex != -1) {
                int qValue = quantity.getSelectedIndex() + 1;
                Product product = ServerApp.products.get(prodIndex);

                if (e.getSource().equals(quantity)) {
                    totalTXT.setText(String.format("$%.2f", product.getPrice() * qValue));
                } else if (e.getSource().equals(add)) {
                    if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Add to cart?", title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        boolean flag = true;
                        for (InvoiceItem item : items) {
                            if (item.getProduct().getIdNum().equals(product.getIdNum())) {
                                flag = false;
                                item.setQuantity(item.getQuantity() + qValue);
                                break;
                            }
                        }

                        if (flag) {
                            InvoiceItem item = new InvoiceItem(
                                    invItemID,
                                    invoice,
                                    product,
                                    product.getName(),
                                    qValue,
                                    product.getPrice()
                            );
                            if (!item.isValid()) {
                                client.log(Level.WARN, "Could not add item! {invalid details}");
                                JOptionPane.showMessageDialog(sOrderPNL.getSPNL().getPnl(), "Invalid Item Details! Try again...", title, JOptionPane.ERROR_MESSAGE);
                                invItemID = (String) client.genID("InvoiceItem", InvoiceItem.idLength);
                                return;
                            } else {
                                items.add(item);
                                sOrderPNL.getCart().getProdIndexes().add(prodIndex);
                            }
                        }
                        sOrderPNL.getCart().update();
                        clear();
                        invItemID = (String) client.genID("InvoiceItem", InvoiceItem.idLength);
                    }
                    add.setSelected(false);
                }
            }
        }

    }
}
