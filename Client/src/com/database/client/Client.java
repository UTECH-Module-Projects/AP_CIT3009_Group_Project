package com.database.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket cs;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;

    public Client() throws IOException {
        createConnection();
        configureStreams();
    }

    private void createConnection() throws IOException {
        cs = new Socket("127.0.0.1", 8888);
    }

    private void configureStreams() throws IOException {
        objOs = new ObjectOutputStream(cs.getOutputStream());
        objIs = new ObjectInputStream(cs.getInputStream());
    }

    public void closeConnection() throws IOException {
        objOs.close();
        objIs.close();
        cs.close();
    }

    public boolean crud(String action, String table, Object entity) throws IOException, ClassNotFoundException {
        objOs.writeObject(action);
        objOs.writeObject(table);
        objOs.writeObject(entity);

        return (Boolean) objIs.readObject();
    }

    public Object get(String table, Object id) throws IOException, ClassNotFoundException {
        objOs.writeObject("get");
        objOs.writeObject(table);
        objOs.writeObject(id);

        return objIs.readObject();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Object> getAll(String table) throws IOException, ClassNotFoundException {
        objOs.writeObject("getAll");
        objOs.writeObject(table);

        return (ArrayList<Object>) objIs.readObject();
    }

    public Object genID(String table, int length) throws IOException, ClassNotFoundException {
        objOs.writeObject("genID");
        objOs.writeObject(table);
        objOs.writeObject(length);

        return objIs.readObject();
    }
}
