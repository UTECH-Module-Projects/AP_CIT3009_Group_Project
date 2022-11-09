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
import com.application.models.tables.*;
import jakarta.persistence.NoResultException;
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
 * @author Barrignton Patternson
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
        configureStreams();
    }

    /**
     * Configures the Objects Streams using the Connection Socket
     *
     * @throws RuntimeException If any fatal errors occur when configuring the object streams
     */
    private void configureStreams() throws RuntimeException {
        try {
            objOs = new ObjectOutputStream(socket.getOutputStream());
            objIs = new ObjectInputStream(socket.getInputStream());
            Server.log.trace("Object Streams Initialized.");
        } catch (IOException e) {
            Server.log.fatal("I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
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
            Server.log.trace("[Client-" + this.clientID + "] Client Has Disconnected. Total: " + --Server.totClients);
        } catch (IOException e) {
            Server.log.fatal("[Client-" + this.clientID + "] I/O Exception! {" + e.getMessage() + "}");
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
            case "Product" -> Server.prodExeq.getAll();
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
            this.clientID = (String) objIs.readObject();
            //Indefinite While Loop which terminates when the action is 'exit'
            do {
                //Get Action from Client
                String action = (String) objIs.readObject();

                //If Action is Exit
                if (action.equals("exit")) {
                    Server.log.info("[Client-" + this.clientID + "] 'exit' operation performed.");
                    this.closeConnection();
                    return;
                }

                //Get Specified Table from Client
                String table = (String) objIs.readObject();

                //Switch case on Action to Perform
                switch (action) {

                    //Merged Cases for Create, Update, and Delete Operations
                    case "create", "update", "delete" -> {

                        //Get entity record from Client
                        Object entity = objIs.readObject();

                        try {

                            //Switch Case for Create, Update, and Delete
                            switch (action) {
                                case "create" -> create(table, entity);
                                case "update" -> update(table, entity);
                                case "delete" -> delete(table, entity);
                            }

                            //Return operation was successful (true)
                            objOs.writeObject(true);
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(false);
                        }
                    }

                    //Case for Get Operation
                    case "get" -> {

                        //Get the ID Number from Client
                        Object id = objIs.readObject();

                        try {
                            //Attempt to retrieve and return the entity record from the table
                            objOs.writeObject(get(table, id));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            Server.log.warn("[Client-" + this.clientID + "] No Result Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Get Column Operation
                    case "getColumn" -> {

                        //Get the Column Field from the Client
                        String field = (String) objIs.readObject();
                        try {
                            //Attempt to retrieve and return the column data from the table
                            objOs.writeObject(getColumn(table, field));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            Server.log.warn("[Client-" + this.clientID + "] No Result Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Get All Operation
                    case "getAll" -> {
                        try {
                            //Attempt to retrieve and return all the data from the table
                            objOs.writeObject(getAll(table));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            Server.log.warn("[Client-" + this.clientID + "] No Result Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    //Case for Generate ID Operation
                    case "genID" -> {

                        //Get the length of the ID Number
                        int length = (int) objIs.readObject();

                        try {
                            //Attempt to generate and return a unique id based on existing id numbers from the table
                            objOs.writeObject(genID(table, length));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (ClassCastException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Class Cast Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            Server.log.warn("[Client-" + this.clientID + "] No Result Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    //Merged Cases for Find Match, Find Match All Operations
                    case "findMatch", "findMatchAll" -> {

                        //Get all SQL conditions
                        SQLCondBuilder[] conditions = (SQLCondBuilder[]) objIs.readObject();

                        try {
                            //Attempt to retrieve and return any matches
                            switch (action) {
                                case "findMatch" -> objOs.writeObject(findMatch(table, conditions));
                                case "findMatchAll" -> objOs.writeObject(findMatchAll(table, conditions));
                            }
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientID + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (NoResultException e) {
                            Server.log.warn("[Client-" + this.clientID + "] No Result Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }
                }
            } while (true);
        } catch (ClassNotFoundException e) {
            Server.log.fatal("[Client-" + this.clientID + "] Class Not Found Exception! {" + e.getMessage() + "}");
        } catch (InvalidClassException e) {
            Server.log.fatal("[Client-" + this.clientID + "] Invalid Class Exception! {" + e.getMessage() + "}");
        } catch (StreamCorruptedException e) {
            Server.log.fatal("[Client-" + this.clientID + "] Stream Corrupted Exception! {" + e.getMessage() + "}");
        } catch (OptionalDataException e) {
            Server.log.fatal("[Client-" + this.clientID + "] Optional Data Exception! {" + e.getMessage() + "}");
        } catch (NotSerializableException e) {
            Server.log.fatal("[Client-" + this.clientID + "] Not Serializable Exception! {" + e.getMessage() + "}");
        } catch (EOFException e) {
            Server.log.fatal("[Client-" + this.clientID + "] End of File Exception! {" + e.getMessage() + "}");
        } catch (IOException e) {
            Server.log.fatal("[Client-" + this.clientID + "] I/O Exception! {" + e.getMessage() + "}");
        } catch (Exception e) {
            Server.log.fatal("[Client-" + this.clientID + "] Unknown error occurred! {" + e.getMessage() + "}");
        }

        //Close the connection after fatal error
        this.closeConnection();
    }
}
