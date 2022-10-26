package com.database.session;

import com.application.models.Customer;
import com.application.models.DateConverter;
import com.application.models.Person;
import com.application.models.Table;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() throws HibernateException {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(Table.class)
                    .addAnnotatedClass(DateConverter.class)
                    .buildSessionFactory();

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        //Question 11
        if (sessionFactory != null)
            sessionFactory.close();
    }
}

