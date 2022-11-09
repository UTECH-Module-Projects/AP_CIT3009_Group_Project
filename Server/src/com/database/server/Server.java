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
package com.database.server;

//Imported Libraries

import com.application.generic.GenericJPADAO;
import com.application.models.tables.*;
import com.application.view.ServerApp;
import com.database.session.SessionFactoryBuilder;
import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1>Server Application Class</h1>
 * <p>
 *     This Class is designed to initialize the root server for the Server Client Architecture.
 *     Multiple Clients may connect to the Server using Threads. Requests may be sent through sockets
 *     and the Server executes these requests and returns appropriate responses.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patternson
 * @version 1.0
 */
public class Server {
    /**
     * {@link SessionFactory} - Used to Generate the Entity Manager
     */
    private SessionFactory sf = SessionFactoryBuilder.getSessionFactory();

    /**
     * {@link EntityManager} - Used to perform JPA DAO operations on the database
     */
    private EntityManager em;

    /**
     * {@link ServerSocket} - Used to create a socket connection between the server and the client
     */
    private ServerSocket ss;

    /**
     * {@link GenericJPADAO} - Executables used to perform generic operations on the database
     */
    public static GenericJPADAO<Customer, String> custExeq;
    public static GenericJPADAO<Employee, String> empExeq;
    public static GenericJPADAO<Invoice, Integer> invExeq;
    public static GenericJPADAO<Product, String> prodExeq;
    public static GenericJPADAO<Department, String> depExeq;

    /**
     * Keeps track of the total number of clients
     */
    public static int totClients = 0;

    /**
     * Keeps track of the number of clients who have connected overtime
     */
    public static int clientCount = 0;

    /**
     * The Path for the boot file
     */
    private static final String bootPath = "Server/src/com/database/boot/boot.txt";

    /**
     * {@link Logger} - Used to log all activities during the session
     */
    public static final Logger log = LogManager.getLogger(Server.class);

    /**
     * Default Constructor - Initializes the Server
     *
     * @throws RuntimeException If any fatal errors occur during the session
     */
    public Server() throws RuntimeException {
        this.getSessionFactory();
        this.getEntityManager();
        this.createTableExecutors();
        this.checkDatabaseBoot();
        this.createConnection();
        this.waitForRequests();
    }

    /**
     * Gets the Session Factory from the Session Factory Builder Class
     *
     * @throws RuntimeException If any fatal errors occur when getting the Session Factory
     */
    private void getSessionFactory() throws RuntimeException {
        try {
            sf = SessionFactoryBuilder.getSessionFactory();
            log.trace("Session factory generated.");
        } catch (HibernateException e) {
            log.fatal("Hibernate Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the Entity Manager from the Session Factory
     *
     * @throws RuntimeException If any fatal errors occur when getting the Entity Manager
     */
    private void getEntityManager() throws RuntimeException {
        try {
            Session s = sf.getCurrentSession();
            Transaction t = s.getTransaction();
            t.begin();
            em = s.getEntityManagerFactory().createEntityManager();
            t.commit();
            log.trace("Entity manager generated.");
        } catch (HibernateException e) {
            log.fatal("Hibernate Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the Generic Table Executors for Each Database Table
     */
    private void createTableExecutors() {
        custExeq = new GenericJPADAO<>(Customer.class, this.em);
        empExeq = new GenericJPADAO<>(Employee.class, this.em);
        invExeq = new GenericJPADAO<>(Invoice.class, this.em);
        prodExeq = new GenericJPADAO<>(Product.class, this.em);
        depExeq = new GenericJPADAO<>(Department.class, this.em);
        log.trace("Generic table executors generated.");
    }

    /**
     * Checks if the Database has been initialized
     *
     * @throws RuntimeException If any fatal errors occur when checking if the database has booted up before
     */
    private void checkDatabaseBoot() throws RuntimeException {
        //Get Boot File
        File bootFile = new File(bootPath);

        try (Scanner scanner = new Scanner(bootFile)) {

            //If Database has not been initialized
            if (!scanner.nextBoolean()) {
                scanner.nextLine();
                log.trace("Database null. Setting up database...");

                //Add All Department data to the database
                while (scanner.hasNextLine()) {
                    depExeq.create(new Department(scanner.nextLine().split(", ")));
                }
                log.trace("Uploaded default department data to database.");

                //Set Database Initialized Check to true in boot file
                try (FileWriter writer = new FileWriter(bootFile)) {
                    writer.write("true");
                    log.trace("Database setup status set to true.");
                } catch (IOException e) {
                    log.fatal("I/O Exception! {" + e.getMessage() + "}");
                    throw new RuntimeException(e);
                }
            }
            log.trace("Database connection verified.");
        } catch (FileNotFoundException e) {
            log.fatal("File Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.fatal("Unknown Error occurred! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new Server Socket for clients to connect to
     *
     * @throws RuntimeException If any fatal errors occur when attempting to create the server socket
     */
    private void createConnection() throws RuntimeException {
        try {
            this.ss = new ServerSocket(8888);
        } catch (IOException e) {
            log.fatal("I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the Connections for the Socket, the Entity Manager, and the Session Factory
     *
     * @throws RuntimeException If any fatal errors occur when attempting to close the connection
     */
    private void closeConnection() throws RuntimeException {
        try {
            ss.close();
            em.close();
            sf.close();
            log.trace("Server Connection closed.");
        } catch (IOException e) {
            log.fatal("I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts Server Application and Waits for Socket Requests from Clients.
     * This creates a new Thread for the Client to perform operations.
     *
     * @throws RuntimeException If any fatal errors occur when waiting for requests from clients
     */
    private void waitForRequests() throws RuntimeException {
        //Generate Admin Page
        ServerApp serverApp = new ServerApp();
        serverApp.start();

        do {
            try {
                //Create new Thread to communicate with Client
                ServerThread thread = new ServerThread(ss.accept());
                thread.start();
                log.trace("New Client has connected. Total: " + ++totClients);
            } catch (SecurityException e) {
                log.fatal("Security Exception! {" + e.getMessage() + "}");
                throw new RuntimeException(e);
            } catch (SocketTimeoutException e) {
                log.fatal("Socket Timeout Exception! {" + e.getMessage() + "}");
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.fatal("I/O Exception! {" + e.getMessage() + "}");
                throw new RuntimeException(e);
            }
        } while (!serverApp.isClosed());

        //End Connection When Admin Page is closed
        closeConnection();
    }
}
