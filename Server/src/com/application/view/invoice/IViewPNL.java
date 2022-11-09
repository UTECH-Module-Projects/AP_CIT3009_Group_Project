package com.application.view.invoice;

import com.application.models.misc.Date;
import com.application.models.tables.Invoice;
import com.application.view.IPNL;
import com.application.view.invoice.view.IViewFormPNL;
import com.application.view.invoice.view.IViewSCHPNL;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class IViewPNL {
    private IPNL ipnl;
    public JPanel pnl;
    public JTable tbl;
    public JScrollPane sPNE;

    public List<Invoice> invoices = new ArrayList<>();

    public IViewSCHPNL search;
    public IViewFormPNL form;

    public IViewPNL(IPNL ipnl) {
        this.ipnl = ipnl;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        List<String[]> strInv = new ArrayList<>();
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid][grow 0]", "[grow 0][]"));

        invoices.add(new Invoice(1005, new Date(12, 12, 2022), "sdjhj34", "Yjsw8dsn", 23.32, 12002.00));
        invoices.add(new Invoice(1006, new Date(12, 12, 2022), "sdjhj34", "Yjsw8dsn", 23.32, 12002.00));
        invoices.add(new Invoice(1007, new Date(12, 12, 2022), "sdjhj34", "Yjsw8dsn", 23.32, 12002.00));
        invoices.forEach(invoice -> strInv.add(invoice.toArray()));
        tbl = new JTable(strInv.toArray(new String[0][0]), Invoice.fields);
        sPNE = new JScrollPane(tbl);
        search = new IViewSCHPNL(this);
//        form = new IViewFormPNL();
    }

    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(sPNE, "grow 100, wrap");
//        pnl.add(form.pnl, "grow 0, wrap");
    }
}
