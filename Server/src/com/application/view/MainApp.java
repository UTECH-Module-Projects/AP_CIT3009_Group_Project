package com.application.view;

import com.database.server.Client;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.application.view.ServerApp.*;

public class MainApp {

    private JFrame frame;
    /**
     * {@link JTabbedPane} The Side Tabbed Pane used to hold the main panels
     */
    private JTabbedPane sideTPNE;
    
    private final Client client;
    
    
    public MainApp(Client client) {
        this.client = client;
        initializeComponents();
        addComponents();
        setProperties();
    }

    /**
     * initializing components within frame
     */
    private void initializeComponents() {
        frame = new JFrame("Admin Page");
        frame.setLayout(new MigLayout("fill"));

        sideTPNE = new JTabbedPane(JTabbedPane.LEFT);

        sePNL = new SEPNL(client);
        sPNL = new SPNL(client);
        cPNL = new CPNL(client);
        iPNL = new IPNL(client);
        invPNL = new INVPNL(client);
        ePNL = new EPNL(client);
        rPNL = new RPNL(client);
    }

    /**
     * Adds side Panes to frame with unique tab icons
     */
    private void addComponents() {
        if (ServerApp.logEmp.getType().equals("Admin")) {
            sideTPNE.addTab(" Server", new ImageIcon(serverIMG), sePNL.getPnl());
        }
        sideTPNE.addTab(" Sales", new ImageIcon(salesIMG), sPNL.getPnl());
        sideTPNE.addTab(" Customer", new ImageIcon(custIMG), cPNL.getPnl());

        if (ServerApp.logEmp.getType().equals("Admin") || ServerApp.logEmp.getType().equals("Manager")) {
            sideTPNE.addTab(" Invoice", new ImageIcon(invIMG), iPNL.getPnl());
            sideTPNE.addTab(" Inventory", new ImageIcon(prodIMG), invPNL.getPnl());
            sideTPNE.addTab(" Employee", new ImageIcon(empIMG), ePNL.getPnl());
            sideTPNE.addTab(" Reports", new ImageIcon(repIMG), rPNL.getPnl());
        }

        frame.add(sideTPNE, "grow");
    }

    /**
     * Sets the window properties of the frame
     *
     * @throws RuntimeException If any fatal errors occur when configuring the object streams
     */
    private void setProperties() throws RuntimeException {
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setIconImage(new ImageIcon(logoIMG).getImage());
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_NO_OPTION) {
                    client.closeConnection();
                    System.exit(0);
                }
            }
        });
    }
}
