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
package com.database.client;

//Imported Libraries

import com.application.generic.SQLCondBuilder;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
    @Getter
    private final String id;
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
        log.trace("[Client-" + this.id + "] Socket Established.");
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
            log.trace("[Client-" + this.id + "] Object Streams Initialized.");
        } catch (IOException e) {
            log.fatal("[Client-" + this.id + "] I/O Exception! {" + e.getMessage() + "}");
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
            log.trace("[Client-" + this.id + "] Connections to Server closed.");
        } catch (IOException e) {
            log.fatal("[Client-" + this.id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        }
    }

    private void start(String action, String table, Object obj) throws RuntimeException {
        try {
            //Send required data for operation
            objOs.writeObject(action);
            objOs.writeObject(table);
            if (obj != null)
                objOs.writeObject(obj);
        } catch (InvalidClassException e) {
            log(Level.FATAL, "Invalid Class Exception!", e);
            throw new RuntimeException(e);
        } catch (NotSerializableException e) {
            log(Level.FATAL, "Not Serializable Exception!", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log(Level.FATAL, "I/O Exception!", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown Exception!", e);
            throw new RuntimeException(e);
        }
    }

    private Object returnObject(String action, String table) throws RuntimeException {
        try {
            log(Level.TRACE, action + " operation performed on " + table + ".");

            return objIs.readObject();
        } catch (InvalidClassException e) {
            log(Level.FATAL, "Invalid Class Exception!", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log(Level.FATAL, "Class Not Found Exception!", e);
            throw new RuntimeException(e);
        } catch (StreamCorruptedException e) {
            log(Level.FATAL, "Stream Corrupted Exception!", e);
            throw new RuntimeException(e);
        } catch (OptionalDataException e) {
            log(Level.FATAL, "Optional Data Exception!", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log(Level.FATAL, "I/O Exception!", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown Exception!", e);
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Object> returnList(String action, String table) throws RuntimeException {
        try {
            log(Level.TRACE, action + " operation performed on " + table + ".");

            return (List<Object>) objIs.readObject();
        } catch (ClassNotFoundException e) {
            log.fatal("[Client-" + this.id + "] Class Not Found Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.fatal("[Client-" + this.id + "] I/O Exception! {" + e.getMessage() + "}");
            throw new RuntimeException(e);
        } catch (Exception e) {
            log(Level.FATAL, "Unknown Exception!", e);
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
    public Object cud(String action, String table, Object entity) throws RuntimeException {
        start(action, table, entity);
        return returnObject(action, table);
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
        start("get", table, id);
        return returnObject("get", table);
    }

    /**
     * Retrieves the data from a column of a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param field The name of the column being retrieved (eg: address)
     * @return A list containing the specified data for a column
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public List<Object> getColumn(String table, String field) throws RuntimeException {
        start("getColumn", table, field);
        return returnList("getColumn", table);
    }

    /**
     * Retrieves all entity records from a table
     *
     * @param table The table to perform the action on (eg: Customer)
     * @return A list of entity records retrieved from the table
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public List<Object> getAll(String table) throws RuntimeException {
        start("getAll", table, null);
        return returnList("getAll", table);
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
        start("genID", table, length);
        return returnObject("genID", table);
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
        start("findMatch", table, conditions);
        return returnObject("findMatch", table);
    }

    /**
     * Retrieves entity records using a collection of SQL conditions
     *
     * @param table The table to perform the action on (eg: Customer)
     * @param conditions A collection of SQL conditions used to search for entity records
     * @return All entity records which match the condition
     * @throws RuntimeException If any fatal errors occur when attempting to performing the operation
     */
    public List<Object> findMatchAll(String table, SQLCondBuilder...conditions) throws RuntimeException {
        start("findMatchAll", table, conditions);
        return returnList("findMatchAll", table);
    }

    public void log(Level level, String msg) {
        log.log(level, "[Client-" + id + "] " + msg);
    }

    public void log(Level level, String msg, Exception ex) {
        log.log(level, "[Client-" + id + "] " + msg + " {" + ex.getMessage() + "}");
    }
}
