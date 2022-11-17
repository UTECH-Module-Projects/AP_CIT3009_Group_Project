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
    public static final String[] levels = new String[]{"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"};
    public static final String[] types = new String[]{"Server", "Client"};


    /**
     * {@link CPNL} The Customer Main Panel
     */
    public static Client client;

    public static TableList<Customer, String> customers;
    public static TableList<Employee, String> employees;
    public static TableList<Invoice, Integer> invoices;
    public static TableList<Product, String> products;
    public static TableList<Department, String> departments;
    public static TableList<Log, String> logs;

    public static CPNL cPNL;
    public static IPNL iPNL;
    public static INVPNL invPNL;
    public static RPNL rPNL;
    public static SPNL sPNL;
    public static EPNL ePNL;
    public static SEPNL sePNL;

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
    public static final String checkoutIMG = "./Images/icon_checkout.png";
    public static final String serverIMG = "./Images/icon_server.png";
    public static final String logoIMG = "./Images/logo.png";

    public static LoginPage loginPage;
    public static String[] depNames;

    public static Employee logEmp;

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
        logs = new TableList<>(Log.class, client.getAll("Log"), Log.tblHeaders);
        depNames = departments.map(Department::getName).toArray(new String[0]);
    }

    public static void refresh(String table) {
        switch (table) {
            case "Customer" -> customers.refresh(client.getAll(table));
            case "Employee" -> employees.refresh(client.getAll(table));
            case "Invoice" -> invoices.refresh(client.getAll(table));
            case "Product" -> products.refresh(client.getAll(table));
            case "Logs" -> logs.refresh(client.getAll(table));
            case "Department" -> departments.refresh(client.getAll(table));
        }
        update(table);
    }

    public static void update(String table) {
        switch (table) {
            case "Customer" -> {
                sPNL.getOrder().refresh();
                cPNL.getView().refresh();
                iPNL.getView().refresh();
                rPNL.getView().getCustRep().refresh();
            }
            case "Employee" -> {
                sePNL.getView().refresh();
                ePNL.getView().refresh();
                iPNL.getView().refresh();
                rPNL.getView().getEmpRep().refresh();
            }

            case "Invoice" -> {
                iPNL.getView().refresh();
                rPNL.getView().getInvRep().refresh();
            }

            case "Product" -> {
                sPNL.getOrder().refresh();
                iPNL.getView().refresh();
                rPNL.getView().getProdRep().refresh();
            }

            case "Logs" -> {
                sePNL.getView().refresh();
            }

            case "Department" -> {
                ePNL.getView().refresh();
            }
        }
    }

    /**
     * Accepts and perform actions sent from the client
     * Executes action on the database and returns the result
     */
    @Override
    public void run() {
        setLookAndFeel();
        client = new Client();
        getDatabaseData();
        loginPage = new LoginPage();

        Server.log.trace("Admin Page Generated.");
        JOptionPane.showMessageDialog(null, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
