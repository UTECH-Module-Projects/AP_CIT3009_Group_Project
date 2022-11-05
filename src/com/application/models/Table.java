package com.application.models;

import com.database.session.SessionFactoryBuilder;
// import jakarta.persistence.TypedQuery;
// import jakarta.persistence.criteria.CriteriaBuilder;
// import jakarta.persistence.criteria.CriteriaQuery;
// import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

// import java.util.Collections;
// import java.util.List;

public abstract class Table {
    public void create() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.persist(this);
            transaction.commit();
            System.out.println(this + " was Added to Database!");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.merge(this);
            transaction.commit();
            System.out.println(this + " was Updated to Database!");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            session.remove(this);
            transaction.commit();
            System.out.println(this + " was Removed from Database!");
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
