package com.application.view.invoice;

import com.application.generic.SQLCond;
import com.application.generic.SQLCondBuilder;
import com.application.generic.SQLType;
import com.application.generic.TableList;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.IPNL;
import com.application.view.ServerApp;
import com.application.view.invoice.view.IViewReceiptPNL;
import com.application.view.invoice.view.IViewSCHPNL;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class IViewPNL implements ListSelectionListener {
    private final IPNL ipnl;
    private final Client client;
    private final TableList<InvoiceItem, String> invoiceItems;
    private JPanel pnl;

    private GUIEntityTable invTable;
    private IViewSCHPNL search;

    private IViewReceiptPNL receipt;


    public IViewPNL(IPNL ipnl, Client client) {
        this.ipnl = ipnl;
        this.client = client;
        this.invoiceItems = new TableList<>(InvoiceItem.class, InvoiceItem.headers);
        initializeComponents();
        addComponents();
        setProperties();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid, grow 100]10[grow 0]", "[grow 0][]"));
        invTable = new GUIEntityTable(ServerApp.invoices.to2DArray(), Invoice.headers, true);

        search = new IViewSCHPNL(this, client);
        receipt = new IViewReceiptPNL(this, client);
    }

    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(invTable.getSPNE(), "grow, wrap");
        pnl.add(receipt.getPnl(), "grow 0, wrap");
    }

    private void setProperties() {
        invTable.addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int viewRow = invTable.getTbl().getSelectedRow();

        if (!e.getValueIsAdjusting() && viewRow != -1) {
            receipt.getDateLBL().setText("Date: " + invTable.getValueAt(viewRow, Invoice.BILL_DATE));
            receipt.getTimeLBL().setText("Time: " + invTable.getValueAt(viewRow, Invoice.BILL_TIME));
            receipt.getCustLBL().setText("Customer ID: " + invTable.getValueAt(viewRow, Invoice.CUST_ID));
            receipt.getIdLBL().setText("Invoice #: " + invTable.getValueAt(viewRow, Invoice.ID_NUM));
            receipt.getInvItemTBL().refresh(invoiceItems.refresh(client.findMatchAll("InvoiceItem", new SQLCondBuilder("invoice.idNum", SQLCond.EQ, invTable.getValueAt(viewRow, Invoice.ID_NUM), SQLType.TEXT))).to2DArray());
            receipt.getDiscLBL().setText("Discount: " + invTable.getValueAt(viewRow, Invoice.DISCOUNT));
            receipt.getTotLBL().setText("   Total: " + invTable.getValueAt(viewRow, Invoice.TOTAL));
        }
    }
}


