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

import com.application.models.tables.*;
import org.hibernate.HibernateException;

import java.io.*;
import java.net.Socket;


/**
 * <h1>Server Thread Class</h1>
 * <p>
 * This Class is designed to allow multiple clients to communication with the server simultaneously using Threading
 * A Client will establish a connection socket with the server thread and this class will perform the operations
 * on the database.
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
    private final int clientNum;

    public ServerThread(Socket socket, int clientNum) {
        this.socket = socket;
        this.clientNum = clientNum;
        configureStreams();
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(socket.getOutputStream());
            objIs = new ObjectInputStream(socket.getInputStream());
            Server.log.trace("[Client-" + this.clientNum + "] Object Streams Initialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            objOs.close();
            objIs.close();
            Server.log.trace("[Client-" + this.clientNum + "] Client Has Disconnected. Total: " + --Server.totClients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void create(String table, Object entity) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.create((Customer) entity);
            case "Employee" -> Server.empExeq.create((Employee) entity);
            case "Invoice" -> Server.invExeq.create((Invoice) entity);
            case "Product" -> Server.prodExeq.create((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (opp: create, table: " + table + ")");
        }
    }

    private void update(String table, Object entity) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.update((Customer) entity);
            case "Employee" -> Server.empExeq.update((Employee) entity);
            case "Invoice" -> Server.invExeq.update((Invoice) entity);
            case "Product" -> Server.prodExeq.update((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (opp: update, table: " + table + ")");
        }
    }

    private void delete(String table, Object id) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.delete((String) id);
            case "Employee" -> Server.empExeq.delete((String) id);
            case "Invoice" -> Server.invExeq.delete((Integer) id);
            case "Product" -> Server.prodExeq.delete((String) id);
            default -> throw new HibernateException("Invalid Table Name! (opp: delete, table: " + table + ")");
        }
    }

    private Object get(String table, Object id) throws HibernateException {
        return switch (table) {
            case "Customer" -> Server.custExeq.get((String) id);
            case "Employee" -> Server.empExeq.get((String) id);
            case "Invoice" -> Server.invExeq.get((Integer) id);
            case "Product" -> Server.prodExeq.get((String) id);
            default -> throw new HibernateException("Invalid Table Name! (opp: get, table: " + table + ")");
        };
    }

    private Object getColumn(String table, String field) throws HibernateException {
        return switch (table) {
            case "Customer" -> Server.custExeq.getColumn(field);
            case "Employee" -> Server.empExeq.getColumn(field);
            case "Invoice" -> Server.invExeq.getColumn(field);
            case "Product" -> Server.prodExeq.getColumn(field);
            default -> throw new HibernateException("Invalid Table Name! (opp: getColumn, table: " + table + ")");
        };
    }

    private Object getAll(String table) throws HibernateException {
        return switch (table) {
            case "Customer" -> Server.custExeq.getAll();
            case "Employee" -> Server.empExeq.getAll();
            case "Invoice" -> Server.invExeq.getAll();
            case "Product" -> Server.prodExeq.getAll();
            default -> throw new HibernateException("Invalid Table Name! (opp: getAll, table: " + table + ")");
        };
    }

    private Object genID(String table, int length) throws HibernateException, ClassCastException {
        return switch (table) {
            case "Customer" -> Server.custExeq.genID(length);
            case "Employee" -> Server.empExeq.genID(length);
            case "Invoice" -> Server.invExeq.genID(length);
            case "Product" -> Server.prodExeq.genID(length);
            default -> throw new HibernateException("Invalid Table Name! (opp: genID, table: " + table + ")");
        };
    }

    @Override
    public void run() {
        try {
            do {
                String action = (String) objIs.readObject();
                if (action.equals("exit")) {
                    Server.log.trace("[Client-" + this.clientNum + "] 'exit' operation performed.");
                    this.closeConnection();
                    return;
                }
                String table = (String) objIs.readObject();

                switch (action) {
                    case "create", "update", "delete" -> {
                        Object entity = objIs.readObject();
                        try {
                            switch (action) {
                                case "create" -> create(table, entity);
                                case "update" -> update(table, entity);
                                case "delete" -> delete(table, entity);
                            }
                            objOs.writeObject(true);
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(false);
                        }
                    }

                    case "get" -> {
                        Object id = objIs.readObject();
                        try {
                            objOs.writeObject(get(table, id));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    case "getColumn" -> {
                        String field = (String) objIs.readObject();
                        try {
                            objOs.writeObject(getColumn(table, field));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    case "getAll" -> {
                        try {
                            objOs.writeObject(getAll(table));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }

                    case "genID" -> {
                        int length = (int) objIs.readObject();
                        try {
                            objOs.writeObject(genID(table, length));
                        } catch (HibernateException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Hibernate Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (ClassCastException e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Class Cast Exception! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        } catch (Exception e) {
                            Server.log.warn("[Client-" + this.clientNum + "] Unknown error occurred! {" + e.getMessage() + "}");
                            objOs.writeObject(null);
                        }
                    }
                }
            } while (true);
        } catch (ClassNotFoundException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Class Not Found Exception! {" + e.getMessage() + "}");
        } catch (InvalidClassException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Invalid Class Exception! {" + e.getMessage() + "}");
        } catch (StreamCorruptedException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Stream Corrupted Exception! {" + e.getMessage() + "}");
        } catch (OptionalDataException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Optional Data Exception! {" + e.getMessage() + "}");
        } catch (NotSerializableException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Not Serializable Exception! {" + e.getMessage() + "}");
        } catch (EOFException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] End of File Exception! {" + e.getMessage() + "}");
        } catch (IOException e) {
            Server.log.fatal("[Client-" + this.clientNum + "] I/O Exception! {" + e.getMessage() + "}");
        } catch (Exception e) {
            Server.log.fatal("[Client-" + this.clientNum + "] Unknown error occurred! {" + e.getMessage() + "}");
        }
        this.closeConnection();
    }
}
