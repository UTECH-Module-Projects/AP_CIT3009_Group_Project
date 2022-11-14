/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patterson  2008034
 *
 */

//Package
package com.application.view;

//Imported Libraries

import com.application.generic.SQLCond;
import com.application.generic.SQLCondBuilder;
import com.application.generic.SQLType;
import com.application.generic.TableList;
import com.application.models.tables.*;
import com.database.server.Client;
import com.database.server.Server;
import com.formdev.flatlaf.FlatLightLaf;

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
    public static final String repIMG = "./Images/icon_report.png";
    public static final String salesIMG = "./Images/icon_sales.png";
    public static final String printIMG = "./Images/icon_print.png";
    public static final String cancelIMG = "./Images/icon_cancel.png";
    public static final String logoIMG = "./Images/logo.png";

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

    public static TableList<Customer, String> customers;
    public static TableList<Employee, String> employees;
    public static TableList<Invoice, Integer> invoices;
    public static TableList<Product, String> products;
    public static TableList<Department, String> departments;
    public static String[] depNames;

    public static Employee logEmp;


    public ServerApp() {
        this.isClosed = false;
    }

    /**
     * look and feel sets the appearance and behaviour of the swing GUI widgets
     *
     * @throws RuntimeException
     */
    private void setLookAndFeel() throws RuntimeException {
        FlatLightLaf.setup();
        /*try {
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
        }*/
    }

    void getDatabaseData() {
        customers = new TableList<>(Customer.class, client.getAll("Customer"), Customer.headers);
        employees = new TableList<>(Employee.class, client.getAll("Employee"), Employee.headers);
        invoices = new TableList<>(Invoice.class, client.getAll("Invoice"), Invoice.headers);
        products = new TableList<>(Product.class, client.getAll("Product"), Product.headers);
        departments = new TableList<>(Department.class, client.getAll("Department"), Department.headers);
        depNames = departments.map(Department::getName).toArray(new String[0]);
    }

    /**
     * initializing components within frame
     */
    private void initializeComponents() {
        frame = new JFrame("Admin Page");
        frame.setLayout(new MigLayout("fill"));

        sideTPNE = new JTabbedPane(JTabbedPane.LEFT);

        sPNL = new SPNL(this, client);
        cPNL = new CPNL(this, client);
        iPNL = new IPNL(this, client);
        pPNL = new INVPNL(this, client);
        ePNL = new EPNL(this, client);
        rPNL = new RPNL(this, client);
    }

    /**
     * Adds side Panes to frame with unique tab icons
     */
    private void addComponents() {
        sideTPNE.addTab(" Sales", new ImageIcon(salesIMG), sPNL.getPnl());
        sideTPNE.addTab(" Customer", new ImageIcon(custIMG), cPNL.getPnl());
        sideTPNE.addTab(" Invoice", new ImageIcon(invIMG), iPNL.getPnl());
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
        logEmp = (Employee) client.findMatch("Employee", new SQLCondBuilder("idNum", SQLCond.EQ, "aDmInR++t", SQLType.TEXT), new SQLCondBuilder("email", SQLCond.EQ, "admin@janwholesale.com", SQLType.TEXT));
        getDatabaseData();
        setLookAndFeel();
        initializeComponents();
        addComponents();
        setProperties();

        Server.log.trace("Admin Page Generated.");
        //Notify User About Server Connection
        JOptionPane.showMessageDialog(frame, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
