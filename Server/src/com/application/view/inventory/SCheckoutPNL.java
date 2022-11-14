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
package com.application.view.inventory;

import com.application.models.tables.Product;
import com.application.view.SPNL;
import com.application.view.ServerApp;
import com.application.view.utilities.GUIEntityTable;
import com.database.server.Client;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

@Getter
public class SCheckoutPNL {
    private final SPNL spnl;
    private final Client client;
    private JPanel pnl;
    private GUIEntityTable prodTBL;
    private InvForm form;
    private JButton addBTN, viewBTN;

    /**
     * Primary Constructor
     * @param spnl
     * @param client
     */
    public SCheckoutPNL(SPNL spnl, Client client) {
        this.spnl = spnl;
        this.client = client;
        this.initializeComponents();
        this.addComponentsToPanel();
    }

    /**
     * Initializes swing Components used in this search panel
     */
    public void initializeComponents() {
        prodTBL = new GUIEntityTable(ServerApp.products.to2DArray(), Product.headers, true);
        form = new InvForm(this, client);
        pnl = new JPanel();
        pnl.setLayout(new MigLayout("ins 10 10 10 10, flowx, fill", "[nogrid, center]", "[nogrid]"));
        viewBTN = new JButton("View Cart");
        addBTN = new JButton("Add To Cart");
    }

    /**
     * each section being added to the panel with miglayout constraints
     */
    public void addComponentsToPanel() {
        pnl.add(prodTBL.getSPNE(), "wrap");
        pnl.add(form.getPnl(), "wrap");
        pnl.add(addBTN, "width 100, gapx 20px 50px");
        pnl.add(viewBTN, "width 100");
    }
}
