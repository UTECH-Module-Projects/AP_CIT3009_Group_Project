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
package com.database.server;

//Imported Libraries

import com.application.generic.SQLCondBuilder;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * <h1>Client Application Class</h1>
 * <p>
 * This Class is designed to initialize the client application for the Server Client Architecture.
 * A Client connects to the server by requesting a connection socket to configure Object Streams.
 * These Object streams will be used to send data between the client and the server
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public class Client {
    /**
     * {@link String} - The ID Number of the client
     */
    private String id;
    /**
     * {@link Socket} - Connection Socket for the Client
     */
    private Socket cs;
    /**
     * {@link ObjectOutputStream} - Used to send Objects through the established socket stream
     */
    private ObjectOutputStream objOs;
    /**
     * {@link ObjectInputStream} - Used to receive Objects through the established socket stream
     */
    private ObjectInputStream objIs;
    /**
     * {@link Logger} - Used to log all activities during the session
     */
    @Getter
    private final Logger log;

    /**
     * Default Constructor - Initializes the Client
     *
     * @throws RuntimeException If any fatal errors occur generating the client
     */
    public Client(String id) throws RuntimeException {
        this.id = id;
        log = LogManager.getLogger(Client.class);
        createConnection();
        configureStreams();
    }

    /**
     * Requests a new Connection Socket from the Server
     *
     * @throws RuntimeException If any fatal errors occur when attempting to connect to the server
     */
    private void createConnection() throws RuntimeException {
        try {
            cs = new Socket("127.0.0.1", 8888);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.trace("[Client-" + id + "] Socket Established.");
    }

    /**
     * Configures the Objects Streams using the Connection Socket
     *
     * @throws RuntimeException If any fatal errors occur when configuring the object streams
     */
    private void configureStreams() throws RuntimeException {
        try {
            objOs = new ObjectOutputStream(cs.getOutputStream());
            objIs = new ObjectInputStream(cs.getInputStream());
            objOs.writeObject(id);
            log.trace("[Client-" + id + "] Object Streams Initialized.");
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the Connection between the Client and Server
     *
     * @throws RuntimeException If any fatal errors occur when closing the connection from the server
     */
    public void closeConnection() throws RuntimeException {
        try {
            objOs.writeObject("exit");
            objOs.close();
            objIs.close();
            cs.close();
            log.trace("[Client-" + id + "] Connections to Server closed.");
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs a CUD (create, update, or delete) operation on a table using an entity
     *
     * @param action The action to be performed (create, update, delete)
     * @param table  The table to perform the action on (eg: Customer)
     * @param entity The entity to be applied on the table
     * @return Whether the operation was successful (true or false)
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public boolean cud(String action, String table, Object entity) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject(action);
            objOs.writeObject(table);
            objOs.writeObject(entity);
            log.trace("[Client-" + id + "] '" + action + "' operation performed on " + table + " table.");

            //Return result of operation (true or false)
            return (Boolean) objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an entity record from a table using an id
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param id The ID Number of the entity being retrieved
     * @return The retrieved entity record
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public Object get(String table, Object id) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("get");
            objOs.writeObject(table);
            objOs.writeObject(id);
            log.trace("[Client-" + id + "] 'get' operation performed on " + table + " (" + id + ").");

            //Return result of operation (entity record)
            return objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the data from a column of a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param field The name of the column being retrieved (eg: address)
     * @return A list containing the specified data for a column
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> getColumn(String table, String field) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("getColumn");
            objOs.writeObject(table);
            objOs.writeObject(field);
            log.trace("[Client-" + id + "] getColumn operation performed on " + table + " (" + field + ").");

            //Return result of operation (list of column data)
            return (ArrayList<Object>) objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all entity records from a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @return A list of entity records retrieved from the table
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> getAll(String table) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("getAll");
            objOs.writeObject(table);
            log.trace("[Client-" + id + "] getAll operation performed on " + table + ".");

            //Return result of operation (list of all entity records)
            return (ArrayList<Object>) objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a unique ID Number using the existing entity ID Numbers from a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param length The length of the ID Number
     * @return The generated ID Number
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public Object genID(String table, int length) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("genID");
            objOs.writeObject(table);
            objOs.writeObject(length);
            log.trace("[Client-" + id + "] genID operation performed on " + table + ".");

            //Return result of operation (unique id number)
            return objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an entity record using a collection of SQL conditions
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param conditions A collection of SQL conditions used to search for entity records
     * @return The first entity record which matches the condition
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public Object findMatch(String table, SQLCondBuilder...conditions) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("findMatch");
            objOs.writeObject(table);
            objOs.writeObject(conditions);
            log.trace("[Client-" + id + "] findMatch operation performed on " + table + ".");

            //Return result of operation (entity record)
            return objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves entity records using a collection of SQL conditions
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param conditions A collection of SQL conditions used to search for entity records
     * @return All entity records which match the condition
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> findMatchAll(String table, SQLCondBuilder...conditions) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject("findMatchAll");
            objOs.writeObject(table);
            objOs.writeObject(conditions);
            log.trace("[Client-" + id + "] findMatch operation performed on " + table + ".");

            //Return result of operation (list of entity records)
            return (ArrayList<Object>) objIs.readObject();
        } catch (InvalidClassException e) {
            log.fatal("[Client-" + id + "] Invalid Class Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log.fatal("[Client-" + id + "] Not Serializable Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }
}
