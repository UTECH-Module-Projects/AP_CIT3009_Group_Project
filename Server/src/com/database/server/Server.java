/**
 * Advance Programming Group Project
 * Date of Submission:
 * Lab Supervisor: Christopher Panther
 * <p>
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 * <p>
 */

//Package
package com.database.server;

//Imported Libraries

import com.application.dao.generic.GenericJPADAO;
import com.application.models.tables.*;
import com.database.session.SessionFactoryBuilder;
import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1>Server Application Class</h1>
 * <p>
 * This Class is designed to initialize the root server for the Server Client Architecture.
 * Multiple Clients may connect to the Server using Threads. Requests may be sent through sockets
 * and the Server executes these requests and returns appropriate responses.
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
     * {@link SessionFactory} used to Generate the Entity Manager
     */
    private SessionFactory sf = SessionFactoryBuilder.getSessionFactory();

    /**
     * {@link EntityManager} used to perform JPA DAO operations on the database
     */
    private EntityManager em;

    /**
     * {@link ServerSocket} used to create a socket connection between the server and the client
     */
    private ServerSocket ss;

    /**
     * {@link GenericJPADAO} executables used to perform generic operations on the database
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
     * The Path for the boot file
     */
    private static final String bootPath = "src/com/database/properties/boot/boot.txt";

    /**
     * {@link Logger} used to log all activities during the session
     */
    public static Logger log = LogManager.getLogger(Server.class);

    /**
     * Default Constructor - Initializes the Server
     *
     * @throws IOException
     * @throws RuntimeException
     */
    public Server() throws IOException, RuntimeException {
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
     * @throws HibernateException
     */
    private void getSessionFactory() throws HibernateException {
        sf = SessionFactoryBuilder.getSessionFactory();
        log.trace("Session factory generated.");
    }

    /**
     * Gets the Entity Manager from the Session Factory
     *
     * @throws HibernateException
     */
    private void getEntityManager() throws HibernateException {
        Session s = sf.getCurrentSession();
        Transaction t = s.getTransaction();
        t.begin();
        em = s.getEntityManagerFactory().createEntityManager();
        t.commit();
        log.trace("Entity manager generated.");
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
     * @throws RuntimeException
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
                    log.fatal("Boot File could not be found!");
                    throw new RuntimeException(e);
                }
            }
            log.trace("Database connection verified.");
        } catch (FileNotFoundException e) {
            log.fatal("Boot File could not be found!");
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.fatal("Unknown error occurred!");
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new Server Socket for clients to connect to
     * @throws IOException
     */
    private void createConnection() throws IOException {
        this.ss = new ServerSocket(8888);
    }

    /**
     * Closes the Connections for the Socket, the Entity Manager, and the Session Factory
     * @throws IOException
     */
    private void closeConnection() throws IOException {
        ss.close();
        em.close();
        sf.close();
    }

    /**
     * Starts Server Application and Waits for Socket Requests from Clients.
     * This creates a new Thread for the Client to perform operations.
     * @throws IOException
     */
    private void waitForRequests() throws IOException {
        JOptionPane.showMessageDialog(null, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
        do {
            ThreadedClass thread = new ThreadedClass(ss.accept());
            thread.start();
        } while (totClients > 0);
        closeConnection();
    }
}
