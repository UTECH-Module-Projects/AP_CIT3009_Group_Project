package com.database.server;

import com.application.dao.generic.GenericJPADAO;
import com.application.models.converters.InvoiceItemID;
import com.application.models.tables.*;
import com.database.session.SessionFactoryBuilder;
import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static SessionFactory sf = SessionFactoryBuilder.getSessionFactory();
    private static EntityManager em;
    private ServerSocket ss;

    public static GenericJPADAO<Customer, String> custExeq;
    public static GenericJPADAO<Employee, String> empExeq;
    public static GenericJPADAO<Invoice, Integer> invExeq;
    public static GenericJPADAO<InvoiceItem, InvoiceItemID> invItemExeq;
    public static GenericJPADAO<Product, String> prodExeq;

    public static int totClients = 0;

    public Server() throws HibernateException, IOException {
        this.getSessionFactory();
        this.getEntityManager();
        this.createGenericExecutors();
        this.createConnection();
        this.waitForRequests();
    }

    private void getSessionFactory() throws HibernateException {
        sf = SessionFactoryBuilder.getSessionFactory();
    }

    private void getEntityManager() throws HibernateException {
        Session s = sf.getCurrentSession();
        Transaction t = s.getTransaction();
        t.begin();
        em = s.getEntityManagerFactory().createEntityManager();
        t.commit();
    }

    private void createGenericExecutors() {
        custExeq = new GenericJPADAO<>(Customer.class, em);
        empExeq = new GenericJPADAO<>(Employee.class, em);
        invExeq = new GenericJPADAO<>(Invoice.class, em);
        invItemExeq = new GenericJPADAO<>(InvoiceItem.class, em);
        prodExeq = new GenericJPADAO<>(Product.class, em);
    }

    private void createConnection() throws IOException {
        this.ss = new ServerSocket(8888);
    }

    private void waitForRequests() throws IOException {
        JOptionPane.showMessageDialog(null, "Server Connected!", "Server Status", JOptionPane.INFORMATION_MESSAGE);
        do {
            ThreadedClass thread = new ThreadedClass(ss.accept());
            thread.start();
        } while (totClients > 0);
        ss.close();
        em.close();
        sf.close();
    }
}
