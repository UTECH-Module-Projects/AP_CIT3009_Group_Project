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
package com.application.view;

import com.application.generic.TableList;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.view.sales.SOrderPNL;
import com.database.server.Client;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class SPNL {
    private final Client client;
    private JPanel pnl;
    private JTabbedPane tPNE;
    private SOrderPNL order;
    @Setter
    private Invoice invoice;
    private final TableList<InvoiceItem, String> items;

    /**
     * Primary Constructor
     * @param client
     */
    public SPNL(Client client) {
        this.client = client;
        items = new TableList<>(InvoiceItem.class, InvoiceItem.headers);
        resetInvoice();
        initializeComponents();
        addComponents();
    }

    /**
     * Initializes swing Components used in this panel
     * Each section of this panel is broken down into separate functioning panels
     */
    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, ins 10 10 10 0"));
        tPNE = new JTabbedPane();

        order = new SOrderPNL("Place Order", this, client, invoice, items);
    }

    /**
     * adding swing components to the panel
     */
    private void addComponents() {
        tPNE.add("Place Order", order.getPnl());
        pnl.add(tPNE, "grow");
    }

    public void resetInvoice() {
        invoice = new Invoice();
        invoice.setIdNum((int) client.genID("Invoice", Invoice.idLength));
        invoice.setEmployee(ClientApp.logEmp);
        items.clear();
    }
}
