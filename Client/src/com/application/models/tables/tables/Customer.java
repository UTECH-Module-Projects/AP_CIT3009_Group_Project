package com.application.models.tables.tables;

import com.application.models.misc.Date;
import com.application.models.misc.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Customer")
public class Customer extends Person {
    public static final int idLength = 8;
    @Column(name = "dom")
    private Date dom;
    @Column(name = "dome")
    private Date dome;

    public Customer() {
        super();
        this.dom = new Date();
        this.dome = new Date();
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
    }

    public Customer(String name, Date dob, String address, String phoneNum, String email) {
        super(name, dob, address, phoneNum, email);
        this.dom = null;
        this.dome = null;
    }

    public Customer(Customer customer) {
        super(customer);
        this.dom = new Date();
        this.dome = new Date();
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
}
