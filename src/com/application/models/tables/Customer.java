package com.application.models.tables;


import com.application.models.Date;
import com.application.models.Person;
import com.application.models.Utilities;
import com.database.session.SessionFactoryBuilder;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Customer")
public class Customer extends Person {
    @Column (name = "dom")
    private Date dom;
    @Column (name = "dome")
    private Date dome;

    public Customer() {
        super();
        this.dom = null;
        this.dome = null;
    }

    public Customer(String idNum, String name, Date dob, String address, String phoneNum, String email, Date dom, Date dome) {
        super(idNum, name, dob, address, phoneNum, email);
        this.dom = dom;
        this.dome = dome;
    }

    public Customer(String idNum, String name, Date dob, String address, String phoneNum, String email) {
        super(idNum, name, dob, address, phoneNum, email);
        this.dom = null;
        this.dome = null;
    }

    public Customer(String name, Date dob, String address, String phoneNum, String email, Date dom, Date dome) {
        super(name, dob, address, phoneNum, email);
        this.dom = dom;
        this.dome = dome;
        this.setIdNum(Utilities.generateUniqueIDString(getIDs(), 8));
    }

    public Customer(String name, Date dob, String address, String phoneNum, String email) {
        super(name, dob, address, phoneNum, email);
        this.dom = null;
        this.dome = null;
        this.setIdNum(Utilities.generateUniqueIDString(getIDs(), 8));
    }

    public Customer(Customer customer) {
        super(customer);
        this.dom = new Date(customer.dom);
        this.dome = new Date(customer.dome);
    }

    public Date getDom() {
        return dom;
    }

    public void setDom(Date dom) {
        this.dom = dom;
    }

    public Date getDome() {
        return dome;
    }

    public void setDome(Date dome) {
        this.dome = dome;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "idNum = '" + idNum + '\'' +
                ", dom = " + dom +
                ", dome = " + dome +
                ", name = '" + name + '\'' +
                ", dob = " + dob +
                ", address = '" + address + '\'' +
                ", phoneNum = '" + phoneNum + '\'' +
                ", email = '" + email + '\'' +
                '}';
    }

    public static Customer get(String id) {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> r = cq.from(Customer.class);
            Customer record = session.createQuery(cq.select(r).where(cb.equal(r.get("idNum"), id))).getSingleResult();
            transaction.commit();
            return record;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static List<Customer> getList() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<Customer> cq = session.getCriteriaBuilder().createQuery(Customer.class);
            List<Customer> list = session.createQuery(cq.select(cq.from(Customer.class))).getResultList();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getIDs() {
        List<Customer> custList = getList();
        List<String> custIDs = new ArrayList<>();

        if (custList != null) {
            for (Customer cust : custList) {
                custIDs.add(cust.getIdNum());
            }
        }

        return custIDs.toArray(new String[0]);
    }
}
