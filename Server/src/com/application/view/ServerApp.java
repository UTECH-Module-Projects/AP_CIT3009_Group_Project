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

//Package
package com.application.view;

//Imported Libraries

import com.database.server.Client;
import com.database.server.Server;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.*;

/**
 * <h1>Server Application Class</h1>
 * <p>
 *     This class is designed to generate a server admin application used to managed all data on the system.
 *     It has super admin privileges, which allows the user to access all of the system's components.
 *     It uses a thread to run the application simultaneously with the server, and it uses a client to communication with the database.
 * </p>
 * <p>
 *     Acronyms:-
 *     c - Customer,
 *     e - Employee,
 *     p - Product,
 *     i - Invoice,
 *     r - Reports,
 *     s - Server,
 *     h - Help,
 *     BTN - Button,
 *     CMB - ComboBox,
 *     IMG - Image,
 *     LBL - Label,
 *     PNL - Panel,
 *     TBL - Table,
 *     TPNE - Tabbed Pane,
 *     TXT - Text Field,
 *     TXTA - Text Area,
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Getter
public class ServerApp extends Thread {
    /**
     * {@link String} Image Paths for all images used in application
     */
    public static final String saveIMG = "./Images/icon_save.png";
    public static final String deleteIMG = "./Images/icon_delete.png";
    public static final String clearIMG = "./Images/icon_clear.png";
    public static final String refIMG = "./Images/icon_refresh.png";
    public static final String custIMG = "./Images/icon_customer.png";
    public static final String empIMG = "./Images/icon_employee.png";
    public static final String prodIMG = "./Images/icon_product.png";
    public static final String invIMG = "./Images/icon_invoice.png";

    /**
     * {@link JFrame} the main frame of the application
     */
    private JFrame frame;
    /**
     * {@link JTabbedPane} The Side Tabbed Pane used to hold the main panels
     */
    private JTabbedPane sideTPNE;
    /**
     * {@link CPNL} The Customer Main Panel
     */
    private CPNL cPNL;
    private IPNL iPNL;
    private Client client;
    private boolean isClosed;

    public ServerApp() {
        this.isClosed = false;
    }

    /**
     * look and feel sets the appearance and behaviour of the swing GUI widgets
     *
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
        sideTPNE.addTab(" Customer", new ImageIcon(custIMG), cPNL.getPnl());
        sideTPNE.addTab(" Invoice", new ImageIcon(invIMG), iPNL.getPnl());
        frame.add(sideTPNE, "grow");
    }

    /**
     * Sets the window properties of the frame
     *
     * @throws RuntimeException If any fatal errors occur when configuring the object streams
     */
    private void setWindowProperties() throws RuntimeException {
        frame.setSize(1000, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
        client = new Client("root");
        setLookAndFeel();
        initializeComponents();
        addSidePanesToFrame();
        setWindowProperties();

        Server.log.trace("Admin Page Generated.");
        //Notify User About Server Connection
        JOptionPane.showMessageDialog(frame, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
