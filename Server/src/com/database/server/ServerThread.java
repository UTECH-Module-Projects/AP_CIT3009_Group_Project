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

import com.application.generic.SQLCondBuilder;
import com.application.models.converters.InvoiceItemID;
import com.application.models.tables.*;
import com.application.view.ServerApp;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import java.io.*;
import java.net.Socket;


/**
 * <h1>Server Thread Class</h1>
 * <p>
 *     This Class is designed to allow multiple clients to communication with the server simultaneously using Threading
 *     A Client will establish a connection socket with the server thread and this class will perform the operations
 *     on the database.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public class ServerThread extends Thread {
    /**
     * {@link Socket} - Connection Socket for the Server Thread
     */
    private final Socket socket;
    /**
     * {@link ObjectOutputStream} - Used to send Objects through the established socket stream
     */
    private ObjectOutputStream objOs;
    /**
     * {@link ObjectInputStream} - Used to receive Objects through the established socket stream
     */
    private ObjectInputStream objIs;

    /**
     * {@link Logger} The logger used to store transactions on the client side
     */
    public static final Logger log = LogManager.getLogger(Client.class);

    /**
     * A unique number assigned to a client
     */
    private String clientID;

    /**
     * Default Constructor - Initializes the Server Thread
     *
     * @param socket The connection socket for the server thread
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    private void log(Level level, String msg) {
        log.log(level, "[" + clientID + "] " + msg);
    }

    private void log(Level level, String msg, Exception ex) {
        log.log(level, "[" + clientID + "] " + msg + " {" + ex.getMessage() + "}");
    }

    /**
     * Configures the Objects Streams using the Connection Socket
     *
     * @throws RuntimeException If any fatal errors occur when configuring the object streams
     */
    private void configureStreams() throws ClassNotFoundException, IOException {
        objOs = new ObjectOutputStream(socket.getOutputStream());
        objIs = new ObjectInputStream(socket.getInputStream());
        this.clientID = (String) objIs.readObject();
        log(Level.TRACE, "Object Streams Initialized.");
    }

    /**
     * Closes the Connection between the Client and Server
     *
     * @throws RuntimeException If any fatal errors occur when closing the connection from the server
     */
    private void closeConnection() throws RuntimeException {
        try {
            objOs.close();
            objIs.close();
        } catch (IOException e) {
            log(Level.FATAL, "I/O Exception!", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown Exception!", e);
            throw new RuntimeException(e);
        }
    }

    public void totClients() {
        try {
            objOs.writeObject(Server.totClients);
        } catch (IOException e) {
            log(Level.FATAL, "I/O Exception!", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown Exception!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new entity record for a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param entity The entity to be applied on the table
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private void create(String table, Object entity) throws HibernateException {
        //Switch Case which applies the action to the selected table
        switch (table) {
            case "Customer" -> Server.custExeq.create((Customer) entity);
            case "Employee" -> Server.empExeq.create((Employee) entity);
            case "Invoice" -> Server.invExeq.create((Invoice) entity);
            case "InvoiceItem" -> Server.invItemExeq.create((InvoiceItem) entity);
            case "Product" -> Server.prodExeq.create((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (opp: create, table: " + table + ")");
        }
    }

    /**
     * Updates an entity record for a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param entity The entity to be applied on the table
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private void update(String table, Object entity) throws HibernateException {
        //Switch Case which applies the action to the selected table
        switch (table) {
            case "Customer" -> Server.custExeq.update((Customer) entity);
            case "Employee" -> Server.empExeq.update((Employee) entity);
            case "Invoice" -> Server.invExeq.update((Invoice) entity);
            case "InvoiceItem" -> Server.invItemExeq.update((InvoiceItem) entity);
            case "Product" -> Server.prodExeq.update((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (opp: update, table: " + table + ")");
        }
    }

    /**
     * Deletes an entity record for a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param id The ID Number of the entity being retrieved
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private void delete(String table, Object id) throws HibernateException {
        //Switch Case which applies the action to the selected table
        switch (table) {
            case "Customer" -> Server.custExeq.delete((String) id);
            case "Employee" -> Server.empExeq.delete((String) id);
            case "Invoice" -> Server.invExeq.delete((Integer) id);
            case "InvoiceItem" -> Server.invItemExeq.delete((InvoiceItemID) id);
            case "Product" -> Server.prodExeq.delete((String) id);
            default -> throw new HibernateException("Invalid Table Name! (opp: delete, table: " + table + ")");
        }
    }

    /**
     * Retrieves an entity record from a table using an id
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param id The ID Number of the entity being retrieved
     * @return The retrieved entity record
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object get(String table, Object id) throws HibernateException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.get((String) id);
            case "Employee" -> Server.empExeq.get((String) id);
            case "Invoice" -> Server.invExeq.get((Integer) id);
            case "InvoiceItem" -> Server.invItemExeq.get((InvoiceItemID) id);
            case "Product" -> Server.prodExeq.get((String) id);
            default -> throw new HibernateException("Invalid Table Name! (opp: get, table: " + table + ")");
        };
    }

    /**
     * Retrieves the data from a column of a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param field The name of the column being retrieved (eg: address)
     * @return A list containing the specified data for a column
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object getColumn(String table, String field) throws HibernateException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.getColumn(field);
            case "Employee" -> Server.empExeq.getColumn(field);
            case "Invoice" -> Server.invExeq.getColumn(field);
            case "InvoiceItem" -> Server.invItemExeq.getColumn(field);
            case "Product" -> Server.prodExeq.getColumn(field);
            default -> throw new HibernateException("Invalid Table Name! (opp: getColumn, table: " + table + ")");
        };
    }

    /**
     * Retrieves all entity records from a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @return A list of entity records retrieved from the table
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object getAll(String table) throws HibernateException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.getAll();
            case "Employee" -> Server.empExeq.getAll();
            case "Invoice" -> Server.invExeq.getAll();
            case "InvoiceItem" -> Server.invItemExeq.getAll();
            case "Product" -> Server.prodExeq.getAll();
            case "Department" -> Server.depExeq.getAll();
            case "Log" -> Server.logExeq.getAll();
            default -> throw new HibernateException("Invalid Table Name! (opp: getAll, table: " + table + ")");
        };
    }

    /**
     * Generates a unique ID Number using the existing entity ID Numbers from a table
     *
     * @param table  The table to perform the action on (eg: Customer)
     * @param length The length of the ID Number
     * @return The generated ID Number
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object genID(String table, int length) throws HibernateException, ClassCastException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.genID(length);
            case "Employee" -> Server.empExeq.genID(length);
            case "Invoice" -> Server.invExeq.genID(length);
            case "InvoiceItem" -> Server.invItemExeq.genID(length);
            case "Product" -> Server.prodExeq.genID(length);
            default -> throw new HibernateException("Invalid Table Name! (opp: genID, table: " + table + ")");
        };
    }

    /**
     * Retrieves an entity record using a collection of SQL conditions
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param conditions A collection of SQL conditions used to search for entity records
     * @return The first entity record which matches the condition
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object findMatch(String table, SQLCondBuilder[] conditions) throws HibernateException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.findMatch(conditions);
            case "Employee" -> Server.empExeq.findMatch(conditions);
            case "Invoice" -> Server.invExeq.findMatch(conditions);
            case "InvoiceItem" -> Server.invItemExeq.findMatch(conditions);
            case "Product" -> Server.prodExeq.findMatch(conditions);
            default -> throw new HibernateException("Invalid Table Name! (opp: findMatch, table: " + table + ")");
        };
    }

    /**
     * Retrieves entity records using a collection of SQL conditions
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param conditions A collection of SQL conditions used to search for entity records
     * @return All entity records which match the condition
     * @throws HibernateException If any fatal errors occur when attempting to performing the operation
     */
    private Object findMatchAll(String table, SQLCondBuilder[] conditions) throws HibernateException {
        //Switch Case which applies the action to the selected table
        return switch (table) {
            case "Customer" -> Server.custExeq.findMatchAll(conditions);
            case "Employee" -> Server.empExeq.findMatchAll(conditions);
            case "Invoice" -> Server.invExeq.findMatchAll(conditions);
            case "InvoiceItem" -> Server.invItemExeq.findMatchAll(conditions);
            case "Product" -> Server.prodExeq.findMatchAll(conditions);
            default -> throw new HibernateException("Invalid Table Name! (opp: findMatchAll, table: " + table + ")");
        };
    }


    /**
     * Accepts and perform actions sent from the client.
     * Executes action on the database and returns the result.
     * Actions: Create, Update, Delete, Get, Get Column, Get All, Generate ID
     */
    @Override
    public void run() {
        try {
            configureStreams();

            //Indefinite While Loop which terminates when the action is 'exit'
            do {
                //Get Action from Client
                String action = (String) objIs.readObject();

                //First switch case
                switch (action) {
                    //Exit Operation
                    case "exit" -> {
                        this.closeConnection();
                        return;
                    }

                    //Logging Operation
                    case "log", "logError" -> {
                        Level level = (Level) objIs.readObject();
                        String msg = (String) objIs.readObject();

                        //If client is logging an error
                        if (action.equals("logError")) {
                            Exception e = (Exception) objIs.readObject();
                            log(level, msg, e);
                        } else {
                            log(level, msg);
                        }
                        continue;
                    }

                    case "sendID" -> {
                        clientID = (String) objIs.readObject();
                        continue;
                    }

                    case "totClients" -> {
                        totClients();
                        continue;
                    }
                }

                //Get Specified Table from Client
                String table = (String) objIs.readObject();
                log(Level.TRACE, "'" + action + "' operation performed on " + table + ".");

                //Switch case on Action to Perform
                switch (action) {

                    //Merged Cases for Create, Update, and Delete Operations
                    case "create", "update", "delete" -> {
                        try {
                            //Get entity record from Client
                            Object entity = objIs.readObject();

                            //Switch Case for Create, Update, and Delete
                            switch (action) {
                                case "create" -> create(table, entity);
                                case "update" -> update(table, entity);
                                case "delete" -> delete(table, entity);
                            }

                            //Return operation was successful (true)
                            objOs.writeObject(true);
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(false);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(false);
                        }
                    }

                    //Case for Get Operation
                    case "get" -> {
                        try {
                            //Get the ID Number from Client
                            Object id = objIs.readObject();

                            //Attempt to retrieve and return the entity record from the table
                            objOs.writeObject(get(table, id));
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Get Column Operation
                    case "getColumn" -> {
                        try {
                            //Get the Column Field from the Client
                            String field = (String) objIs.readObject();

                            //Attempt to retrieve and return the column data from the table
                            objOs.writeObject(getColumn(table, field));
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Get All Operation
                    case "getAll" -> {
                        try {
                            objOs.writeObject(getAll(table));
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Generate ID Operation
                    case "genID" -> {
                        try {
                            //Get the length of the ID Number
                            int length = (int) objIs.readObject();

                            //Attempt to generate and return a unique id based on existing id numbers from the table
                            objOs.writeObject(genID(table, length));
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(null);
                        }
                    }

                    //Merged Cases for Find Match, Find Match All Operations
                    case "findMatch", "findMatchAll" -> {
                        try {
                            //Get all SQL conditions
                            SQLCondBuilder[] conditions = (SQLCondBuilder[]) objIs.readObject();

                            //Attempt to retrieve and return any matches
                            switch (action) {
                                case "findMatch" -> objOs.writeObject(findMatch(table, conditions));
                                case "findMatchAll" -> objOs.writeObject(findMatchAll(table, conditions));
                            }
                        } catch (HibernateException e) {
                            log(Level.WARN, "Hibernate Exception!", e);
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            log(Level.WARN, "No Result Exception!", e);
                            objOs.writeObject(null);
                        }
                    }
                }
            } while (true);
        } catch (ClassNotFoundException e) {
            log(Level.FATAL, "Class Not Found Exception!", e);
        } catch (InvalidClassException e) {
            log(Level.FATAL, "Invalid Class Exception!", e);
        } catch (StreamCorruptedException e) {
            log(Level.FATAL, "Stream Corrupted Exception!", e);
        } catch (OptionalDataException e) {
            log(Level.FATAL, "Optional Data Exception!", e);
        } catch (NotSerializableException e) {
            log(Level.FATAL, "Not Serializable Exception!", e);
        } catch (ClassCastException e) {
            log(Level.FATAL, "Class Cast Exception!", e);
        } catch (EOFException e) {
            log(Level.FATAL, "End of File Exception!", e);
        } catch (IOException e) {
            log(Level.FATAL, "I/O Exception!", e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown error occurred!", e);
        } finally {
            //Close the connection after fatal error
            log(Level.TRACE, "Client Has Disconnected. Total: " + --Server.totClients);
            this.closeConnection();
        }
    }
}
