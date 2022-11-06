package com.database.server;

import com.application.models.converters.InvoiceItemID;
import com.application.models.tables.*;
import org.hibernate.HibernateException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ThreadedClass extends Thread {
    private final Socket socket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;

    public ThreadedClass(Socket socket) {
        this.socket = socket;
        configureStreams();

        Server.totClients++;
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(socket.getOutputStream());
            objIs = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            objOs.close();
            objIs.close();
            --Server.totClients;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void create(String table, Object entity) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.create((Customer) entity);
            case "Employee" -> Server.empExeq.create((Employee) entity);
            case "Invoice" -> Server.invExeq.create((Invoice) entity);
            case "InvoiceItem" -> Server.invItemExeq.create((InvoiceItem) entity);
            case "Product" -> Server.prodExeq.create((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        }
    }

    private void update(String table, Object entity) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.update((Customer) entity);
            case "Employee" -> Server.empExeq.update((Employee) entity);
            case "Invoice" -> Server.invExeq.update((Invoice) entity);
            case "InvoiceItem" -> Server.invItemExeq.update((InvoiceItem) entity);
            case "Product" -> Server.prodExeq.update((Product) entity);
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        }
    }

    private void delete(String table, Object id) throws HibernateException {
        switch (table) {
            case "Customer" -> Server.custExeq.delete((String) id);
            case "Employee" -> Server.empExeq.delete((String) id);
            case "Invoice" -> Server.invExeq.delete((Integer) id);
            case "InvoiceItem" -> Server.invItemExeq.delete((InvoiceItemID) id);
            case "Product" -> Server.prodExeq.delete((String) id);
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        }
    }

    private Object get(String table, Object id) throws HibernateException {
        return switch (table) {
            case "Customer" -> Server.custExeq.get((String) id);
            case "Employee" -> Server.empExeq.get((String) id);
            case "Invoice" -> Server.invExeq.get((Integer) id);
            case "InvoiceItem" -> Server.invItemExeq.get((InvoiceItemID) id);
            case "Product" -> Server.prodExeq.get((String) id);
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        };
    }

    private Object getAll(String table) throws HibernateException {
        return switch (table) {
            case "Customer" -> Server.custExeq.getAll();
            case "Employee" -> Server.empExeq.getAll();
            case "Invoice" -> Server.invExeq.getAll();
            case "InvoiceItem" -> Server.invItemExeq.getAll();
            case "Product" -> Server.prodExeq.getAll();
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        };
    }

    private Object genID(String table, int length) throws HibernateException, ClassCastException {
        Object id = switch (table) {
            case "Customer" -> Server.custExeq.genID(length);
            case "Employee" -> Server.empExeq.genID(length);
            case "Invoice" -> Server.invExeq.genID(length);
            case "InvoiceItem" -> Server.invItemExeq.genID(length);
            case "Product" -> Server.prodExeq.genID(length);
            default -> throw new HibernateException("Invalid Table Name! (" + table + ")");
        };
        System.out.println(id);
        return id;
    }

    @Override
    public void run() {
        String action = "";

        try {
            do {
                try {
                    action = (String) objIs.readObject();
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
                                objOs.writeObject(false);
                            }
                        }

                        case "get" -> {
                            Object id = objIs.readObject();
                            try {
                                objOs.writeObject(get(table, id));
                            } catch (HibernateException e) {
                                objOs.writeObject(null);
                            }
                        }

                        case "getAll" -> {
                            try {
                                objOs.writeObject(getAll(table));
                            } catch (HibernateException e) {
                                objOs.writeObject(null);
                            }
                        }

                        case "genID" -> {
                            int length = (int) objIs.readObject();
                            try {
                                objOs.writeObject(genID(table, length));
                            }  catch (HibernateException | ClassCastException e) {
                                e.printStackTrace();
                                objOs.writeObject(null);
                            }
                        }
                    }
                } catch (ClassNotFoundException | ClassCastException e) {
                    e.printStackTrace();
                }
            } while (!action.equals("exit"));

            this.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
