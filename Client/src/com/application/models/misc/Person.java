package com.application.models.misc;
import jakarta.persistence.*;

import java.io.Serializable;

//Question 17
@MappedSuperclass
public abstract class Person implements Serializable {
    @Id
    @Column(name = "idNum")
    protected String idNum;

    @Column (name = "name")
    protected String name;

    @Column (name = "dob")
    protected Date dob;

    @Column (name = "address")
    protected String address;

    @Column (name = "phoneNum")
    protected String phoneNum;

    @Column (name = "email")
    protected String email;

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

    public Person(String name, Date dob, String address, String phoneNum, String email) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public Person(Person person) {
        this.idNum = person.idNum;
        this.name = person.name;
        this.dob = person.dob;
        this.address = person.address;
        this.phoneNum = person.phoneNum;
        this.email = person.email;
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
