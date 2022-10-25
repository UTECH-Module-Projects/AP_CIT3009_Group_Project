package com.application.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

//Question 17
@Entity (name = "Person")
@Table (name = "Person")
public class Person {
    @Id
    @Column (name = "idNum")
    private String idNum;

    @Column (name = "name")
    private String name;

    @Column (name = "dob")
    private Date dob;

    @Column (name = "address")
    private String address;

    @Column (name = "name")
    private String phoneNum;

    @Column (name = "email")
    private String email;

    public Person() {
        this.idNum = "";
        this.name = "";
        this.dob = new Date();
        this.address = "";
        this.phoneNum = "";
        this.email = "";
    }

    public Person(String idNum, String name, Date dob, String address, String phoneNum, String email) {
        this.idNum = idNum;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
