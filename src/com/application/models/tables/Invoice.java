package com.application.models.tables;

import com.application.models.Date;
import com.application.models.Utilities;
import com.database.session.SessionFactoryBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table (name = "Invoice")
public class Invoice {

    @Id
    @Column (name = "idNum")
    private int idNum;

    @Column (name = "billDate")
    private Date billDate;

    @Column (name = "empID")
    private String empID;

    @Column (name = "custID")
    private String custID;

    @Column (name = "total")
    private double total;

    public Invoice(int idNum, Date billDate, String empID, String custID, double total) {
        this.idNum = idNum;
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.total = total;
    }

    public Invoice(Date billDate, String empID, String custID, double total) {
        this.idNum = Utilities.generateUniqueIDInt(getIDs(), 10);
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.total = total;
    }

    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public static Invoice get(String id) {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
            Root<Invoice> r = cq.from(Invoice.class);
            Invoice record = session.createQuery(cq.select(r).where(cb.equal(r.get("idNum"), id))).getSingleResult();
            transaction.commit();
            return record;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static List<Invoice> getList() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<Invoice> cq = session.getCriteriaBuilder().createQuery(Invoice.class);
            List<Invoice> list = session.createQuery(cq.select(cq.from(Invoice.class))).getResultList();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static int[] getIDs() {
        List<Invoice> invList = getList();
        assert invList != null;
        int[] invIDs = new int[invList.size()];

        for (int i = 0; i < invList.size(); i++) {
            invIDs[i] = invList.get(i).getIdNum();
        }

        return invIDs;
    }
}
