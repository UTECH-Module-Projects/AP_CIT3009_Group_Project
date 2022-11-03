package com.database.session;

import com.application.models.tables.Department;
import com.application.models.tables.Customer;
import com.application.models.converters.DateConverter;
import com.application.models.Person;
import com.application.models.Table;
import com.application.models.tables.Employee;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() throws HibernateException {

        if (sessionFactory == null)
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(Table.class)
                    .addAnnotatedClass(DateConverter.class)
                    .addAnnotatedClass(Department.class)
                    .buildSessionFactory();

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        //Question 11
        if (sessionFactory != null)
            sessionFactory.close();
    }
}

