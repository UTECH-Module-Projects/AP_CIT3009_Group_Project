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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class SOrderFormPNL implements ActionListener {
    private final String title;
    private final SOrderPNL sOrderPNL;
    private final Client client;
    private JPanel pnl;
    private JLabel idLBL, nameLBL, shDescLBL, loDescLBL, stockLBL, priceLBL, quantityLBL, totalLBL;
    private JTextField idTXT, nameTXT, shDescTXT, stockTXT, priceTXT, totalTXT;
    private JTextArea loDescTXTA;
    private JComboBox<String> quantity;
    public JButton add, cancel;
    private Product product;
    private final Invoice invoice;
    private String invItemID;

    private TableList<InvoiceItem, String> items;

    /**
     * Primary Constructor
     * @param title
     * @param sOrderPNL
     * @param client
     */
    public SOrderFormPNL(String title, SOrderPNL sOrderPNL, Client client, Invoice invoice) {
        this.title = title;
        this.sOrderPNL = sOrderPNL;
        this.client = client;
        this.invoice = invoice;
        invItemID = (String) client.genID("InvoiceItem", InvoiceItem.idLength);
        items = new TableList<>(InvoiceItem.class, InvoiceItem.headers);
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * Initializes swing Components used in this form
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 0 10", "[]10[]10[]10[]", "[]5[]5[]5[]5[]5[]5[]5[]15[]"));

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
        totalTXT = new JTextField();

        quantity = new JComboBox<>();

        add = new JButton("Add to Cart", new ImageIcon(ServerApp.salesIMG));
        cancel = new JButton("Cancel", new ImageIcon(ServerApp.cancelIMG));
    }

    /**
     * adding components to the panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(idLBL, "grow");
        pnl.add(idTXT, "grow, span 2, wrap");

        pnl.add(nameLBL, "grow");
        pnl.add(nameTXT, "grow, span 3, wrap");

        pnl.add(shDescLBL, "grow");
        pnl.add(shDescTXT, "grow, span 3, wrap");

        pnl.add(loDescLBL, "grow");
        pnl.add(loDescTXTA, "grow, span 3, wrap");

        pnl.add(stockLBL, "grow");
        pnl.add(stockTXT, "grow, span 2, wrap");

        pnl.add(priceLBL, "grow");
        pnl.add(priceTXT, "grow, span 2, wrap");

        pnl.add(quantityLBL, "grow");
        pnl.add(quantity, "grow, wrap");

        pnl.add(totalLBL, "grow");
        pnl.add(totalTXT, "grow, span 2, wrap");

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

        add.setEnabled(false);

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
        totalTXT.setText("");

        quantity.setSelectedIndex(0);

        add.setEnabled(false);

        sOrderPNL.getProdTBL().clearSelection();
        sOrderPNL.setProdIndex(-1);
        pnl.setVisible(false);
    }

    /**
     * Method updates specific product info in table based on form input
     * @param product
     */
    public void update(Product product) {
        if (sOrderPNL.getSelCust().getCustNames().getSelectedIndex() != 0 && product.getStock() > 0) {
            idTXT.setText(product.getIdNum());
            nameTXT.setText(product.getName());
            shDescTXT.setText(product.getShDesc());
            loDescTXTA.setText(product.getLoDesc());
            stockTXT.setText(String.valueOf(product.getStock()));
            priceTXT.setText(String.format("$%.2f", product.getPrice()));
            totalTXT.setText(String.valueOf(product.getPrice() * (quantity.getSelectedIndex()+1)));

            this.product = product;

            quantity.removeAllItems();
            quantity.setModel(new DefaultComboBoxModel<>(IntStream.range(1, product.getStock()+1).mapToObj(String::valueOf).toArray(String[]::new)));
            quantity.setSelectedIndex(0);
            pnl.setVisible(true);
            add.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int prodIndex = sOrderPNL.getProdIndex();
        if (e.getSource().equals(cancel)) {
            clear();
        } else if (e.getSource().equals(add) && prodIndex != -1) {
            InvoiceItem item;

            if (JOptionPane.showConfirmDialog(sOrderPNL.getSPNL().getPnl(), "Add to cart?", title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

                item = new InvoiceItem(
                        invItemID,
                        invoice,
                        product,
                        Integer.parseInt(quantity.getItemAt(quantity.getSelectedIndex() == -1 ? 0 : quantity.getSelectedIndex())),
                        product.getPrice()
                );
                if (!item.isValid()) {
                    client.log(Level.WARN, "Could not add item! {invalid details}");
                    JOptionPane.showMessageDialog(sOrderPNL.getSPNL().getPnl(), "Invalid Item Details! Try again...", title, JOptionPane.ERROR_MESSAGE);

                    return;
                }
                invItemID = (String) client.genID("InvoiceItem", InvoiceItem.idLength);
                items.add(item);
                sOrderPNL.getCart().getPnl().setVisible(true);
                sOrderPNL.getCart().getCheckout().setEnabled(true);
                sOrderPNL.getCart().getItemTBL().addRow(item.toArray());
                sOrderPNL.getCart().update();
                clear();
            }
        }
    }
}
