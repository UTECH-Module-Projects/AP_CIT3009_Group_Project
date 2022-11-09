package com.database.session;

import com.application.models.converters.InvoiceItemID;
import com.application.models.tables.*;
import com.application.models.converters.DateConverter;
import com.application.models.misc.Person;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
    public static SessionFactory getSessionFactory() throws HibernateException {
        return new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Department.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Invoice.class)
                    .addAnnotatedClass(InvoiceItem.class)
                    .addAnnotatedClass(Product.class)
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(DateConverter.class)
                    .buildSessionFactory();
    }
}

