package com.application.view.invoice.view;

import com.application.models.tables.InvoiceItem;
import com.application.view.invoice.IViewPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

@Getter
public class IViewReceiptPNL {
    private final IViewPNL iViewPNL;
    private final Client client;
    private JPanel pnl;
    private JTextArea txtA;
    private JLabel headerLBL, idLBL, dateLBL, timeLBL, custLBL, empLBL, discLBL, totLBL;
    private GUIEntityTable invItemTBL;

    /**
     * Primary constructor
     * @param iViewPNL
     * @param client
     */
    public IViewReceiptPNL(IViewPNL iViewPNL, Client client) {
        this.iViewPNL = iViewPNL;
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * initializes swing components
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 17 17 17 17", "[grow 50][grow 50]", "[][][][][][]"));

        idLBL = new JLabel("Invoice #:");
        dateLBL = new JLabel("Date:");
        timeLBL = new JLabel("Time:");
        custLBL = new JLabel("Customer ID:");
        empLBL = new JLabel("Employee ID:");
        discLBL = new JLabel("Discount: ");
        totLBL = new JLabel("Total: ");

        invItemTBL = new GUIEntityTable(InvoiceItem.headers, false);
        invItemTBL.getTbl().setShowGrid(false);
    }

    /**
     * add components to panel with miglayout constraints
     */
    private void addComponents() {
        pnl.add(dateLBL, "grow");
        pnl.add(custLBL, "grow, wrap");
        pnl.add(timeLBL, "grow");
        pnl.add(idLBL, "grow, wrap");
        pnl.add(invItemTBL.getSPNE(), "span 2, grow, wrap");
        pnl.add(discLBL, "span 2, wrap");
        pnl.add(totLBL, "span 2, wrap");
    }

    /**
     * sets design  and layout properties of the invoice item table
     */
    private void setProperties() {
        pnl.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createDashedBorder(null, 1, 5, 6, false)));
        pnl.setBackground(Color.white);
        invItemTBL.getTbl().setBorder(BorderFactory.createEmptyBorder());
        invItemTBL.getSPNE().setBorder(BorderFactory.createEmptyBorder());
        invItemTBL.getSPNE().getViewport().setBackground(Color.white);
        invItemTBL.getTbl().getTableHeader().setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));

        invItemTBL.getTbl().getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel t = new JLabel(value.toString());
            Font newLabelFont = new Font(t.getFont().getName(), Font.BOLD, t.getFont().getSize()+2);
            t.setOpaque(false);
            t.setForeground(Color.black);
            t.setFont(newLabelFont);
            return t;
        });
        invItemTBL.getTbl().getTableHeader().setOpaque(false);
        invItemTBL.getTbl().getTableHeader().setForeground(Color.black);
        invItemTBL.getTbl().getTableHeader().setBackground(Color.white);
        invItemTBL.getSPNE().setMaximumSize(new Dimension(300, 300));
        invItemTBL.getTbl().getColumnModel().getColumn(InvoiceItem.PROD_ID).setMinWidth(200);
    }
}
