package com.application.models.tables;

import com.database.session.SessionFactoryBuilder;
// jakarta Persistence

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.persistence.criteria.CriteriaBuilder;
// import jakarta.persistence.criteria.CriteriaQuery;
// import jakarta.persistence.criteria.Root;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


//javax Persistence
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Product")
public class Product extends com.application.models.Table {
    @Id
    @Column (name = "idNum")
    private String idNum;
    @Column (name = "name")
    private String name;
    @Column (name = "shDesc")
    private String shDesc;
    @Column (name = "loDesc")
    private String loDesc;
    @Column (name = "stock")
    private int stock;
    @Column (name = "price")
    private double price;

    public Product() {
        this.idNum = "";
        this.name = "";
        this.shDesc = "";
        this.loDesc = "";
        this.stock = 0;
        this.price = 0.0f;
    }

    public Product(String idNum, String name, String shDesc, String loDesc, int stock, double price) {
        this.idNum = idNum;
        this.name = name;
        this.shDesc = shDesc;
        this.loDesc = loDesc;
        this.stock = stock;
        this.price = price;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShDesc() {
        return shDesc;
    }

    public void setShDesc(String shDesc) {
        this.shDesc = shDesc;
    }

    public String getLoDesc() {
        return loDesc;
    }

    public void setLoDesc(String loDesc) {
        this.loDesc = loDesc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idNum='" + idNum + '\'' +
                ", name='" + name + '\'' +
                ", shDesc='" + shDesc + '\'' +
                ", loDesc='" + loDesc + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }

    public static Product get(String id) {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> r = cq.from(Product.class);
            Product record = session.createQuery(cq.select(r).where(cb.equal(r.get("idNum"), id))).getSingleResult();
            transaction.commit();
            return record;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static List<Product> getList() {
        Transaction transaction = null;

        try (Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<Product> cq = session.getCriteriaBuilder().createQuery(Product.class);
            List<Product> list = session.createQuery(cq.select(cq.from(Product.class))).getResultList();
            transaction.commit();
            return list;
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return null;
    }

    public static String[] getIDs() {
        List<Product> prodList = getList();
        List<String> prodIDs = new ArrayList<>();

        if (prodList != null) {
            for (Product emp : prodList) {
                prodIDs.add(emp.getIdNum());
            }
        }

        return prodIDs.toArray(new String[0]);
    }
}
