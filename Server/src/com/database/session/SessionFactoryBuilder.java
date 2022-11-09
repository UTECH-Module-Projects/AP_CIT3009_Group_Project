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
package com.database.session;

//Imported Libraries

import com.application.models.tables.*;
import com.application.models.converters.DateConverter;
import com.application.models.misc.Person;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <h1>Session Factory Builder Class</h1>
 * <p>
 *     This Class is designed to generate the Session Factory using the Hibernate configuration file,
 *     along with the Hibernate annotations within all of the relevant classes.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public class SessionFactoryBuilder {
    /**
     * Generates the Session Factory used to generate the Entity Manager
     * @return The session factory
     * @throws HibernateException If any fatal errors occur when configuring the session factory
     */
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

