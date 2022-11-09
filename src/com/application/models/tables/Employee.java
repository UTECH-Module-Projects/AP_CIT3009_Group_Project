package com.application.models.tables;


import com.application.models.Date;
import com.application.models.Person;
import com.application.models.Utilities;
import com.database.session.SessionFactoryBuilder;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Table;
 import jakarta.persistence.TypedQuery;
 import jakarta.persistence.criteria.CriteriaBuilder;
 import jakarta.persistence.criteria.CriteriaQuery;
 import jakarta.persistence.criteria.Root;
//import javax.persistence.*;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Employee")
public class Employee extends Person {
    public static final String[] types = {"Cashier", "Line Worker", "Manager", "Supervisor"};
    @Column (name = "type")
    private String type;
    @Column (name = "depCode")
    private String depCode;

    public Employee() {
        super();
        this.type = "";
        this.depCode = "";
    }

    public Employee(String idNum, String name, Date dob, String address, String phoneNum, String email, String type, String depCode) {
        super(idNum, name, dob, address, phoneNum, email);
        this.type = type;
        this.depCode = depCode;
    }

    public Employee(String name, Date dob, String address, String phoneNum, String email, String type, String depCode) {
        super(name, dob, address, phoneNum, email);
        this.type = type;
        this.depCode = depCode;
        this.setIdNum(Utilities.generateUniqueIDString(getIDs(), 8));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "type='" + type + '\'' +
                ", depCode='" + depCode + '\'' +
                ", idNum='" + idNum + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Employee get(String id) {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> r = cq.from(Employee.class);
            Employee record = session.createQuery(cq.select(r).where(cb.equal(r.get("idNum"), id))).getSingleResult();
            transaction.commit();
            return record;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static List<Employee> getList() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<Employee> cq = session.getCriteriaBuilder().createQuery(Employee.class);
            List<Employee> list = session.createQuery(cq.select(cq.from(Employee.class))).getResultList();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getIDs() {
        List<Employee> empList = getList();
        List<String> empIDs = new ArrayList<>();

        if (empList != null) {
            for (Employee emp : empList) {
                empIDs.add(emp.getIdNum());
            }
        }

        return empIDs.toArray(new String[0]);
    }
}
