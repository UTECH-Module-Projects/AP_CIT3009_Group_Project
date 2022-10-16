package com.application.models;

public class Customer extends Person {
    private Date dom;
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
    
}
