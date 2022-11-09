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
package com.application.view.customer;

import com.application.models.tables.Customer;
import com.application.view.CPNL;
import com.application.view.customer.view.CViewFormPNL;
import com.application.view.customer.view.CViewSearchPNL;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Getter
public class CViewPNL implements ActionListener {
    private final CPNL cpnl;
    private JPanel pnl;
    private JTable tbl;
    private JScrollPane sPNE;
    private List<Customer> custList;
    private CViewSearchPNL search;
    private CViewFormPNL form;

    /**
     * Custmer View Panel Default Constructor
     * @param cpnl
     */
    public CViewPNL(CPNL cpnl) {
        this.cpnl = cpnl;
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        pnl = new JPanel(new MigLayout("fill, flowy, ins 13 10 0 10", "[nogrid][grow 0]", "[grow 0][]"));

        custList = cpnl.getServerApp().getClient().getAll("Customer").stream().map(obj -> (Customer) obj).toList();
        tbl = new JTable(custList.stream().map(Customer::toArray).toList().toArray(new String[0][0]), Customer.headers);

        sPNE = new JScrollPane(tbl);
        search = new CViewSearchPNL(this);
        form = new CViewFormPNL(this);
    }

    private void addComponents() {
        pnl.add(search.getPnl(), "aligny top, growx 100, growy 0");
        pnl.add(sPNE, "grow 100, wrap");
        pnl.add(form.pnl, "grow 0, wrap");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
