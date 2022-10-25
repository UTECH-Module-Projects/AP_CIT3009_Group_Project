package com.database.session;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Question
public class SessionFactoryBuilder {
    private static SessionFactory sessionFactory = null;

    //Question 9
    public static SessionFactory getSessionFactory() throws HibernateException {

        //Question 10
        if (sessionFactory == null) {
//            sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
        }

        return sessionFactory;
    }

    //Question 9
    public static void closeSessionFactory() {
        //Question 11
        if (sessionFactory != null)
            sessionFactory.close();
    }
}

