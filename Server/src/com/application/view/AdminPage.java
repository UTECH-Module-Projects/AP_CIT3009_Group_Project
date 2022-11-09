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

import com.database.server.Client;
import com.database.server.Server;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.*;

/**
 * Acronyms:-
 *      c - Customer
 *      p - Product
 *      i - Invoice
 *      PNL - Panel
 *      BTN - Button
 *      CMB - ComboBox
 *      TPNE - Tabbed Pane
 *      V - View
 *
 */
@Getter
public class AdminPage extends Thread {
    private JFrame frame;
    private JTabbedPane sideTPNE;
    private CPNL cPNL;
    private IPNL iPNL;
    private Client client;
    private boolean isClosed;

    public AdminPage() {
        this.isClosed = false;
    }

    /**
     * look and feel sets the appearance and behaviour of the swing GUI widgets
     * @throws RuntimeException
     */
    private void setLookAndFeel() throws RuntimeException {
        //FlatDarkLaf.setup();
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            client.getLog().trace("Look and Feel was set to JTattoo.");
        } catch (ClassNotFoundException e) {
            client.getLog().fatal("Could not find Look and Feel Class! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            client.getLog().fatal("Unable to Instantiate Look and Feel! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            client.getLog().fatal("Unable to Access Look and Feel! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            client.getLog().fatal("Unable to Use Look and Feel! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * initializing components within frame
     */
    private void initializeComponents() {
        frame = new JFrame("Admin Page");
        frame.setLayout(new MigLayout("fill"));

        sideTPNE = new JTabbedPane(JTabbedPane.LEFT);

        cPNL = new CPNL(this);
        iPNL = new IPNL(this);
    }

    /**
     * Adds side Panes to frame with unique tab icons
     */
    private void addSidePanesToFrame() {
        sideTPNE.addTab(" Customer", new ImageIcon("src/com/application/view/images/icon_customer.png"), cPNL.getPnl());
        sideTPNE.addTab(" Invoice", new ImageIcon("src/com/application/view/images/icon_invoice.png"), iPNL.getPnl());
        frame.add(sideTPNE, "grow");
    }

    /**
     * Sets the window properties of the frame
     * @throws RuntimeException  If any fatal errors occur when configuring the object streams
     */
    private void setWindowProperties() throws RuntimeException {
        frame.setSize(1000, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_NO_OPTION) {
                    isClosed = true;
                    client.closeConnection();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Accepts and perform actions sent from the client
     * Executes action on the database and returns the result
     */
    @Override
    public void run() {
        client = new Client();
        client.getLog().trace("Client Established.");
        setLookAndFeel();
        initializeComponents();
        addSidePanesToFrame();
        setWindowProperties();

        Server.log.trace("Admin Page Generated.");
        //Notify User About Server Connection
        JOptionPane.showMessageDialog(frame, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
