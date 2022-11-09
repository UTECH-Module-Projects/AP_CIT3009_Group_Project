/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 *
 */
package com.application.models.misc;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
/**
 * <h1>Person Class</h1>
 * <p>
 * This is the Person class
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 * */

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

    /**
     * Default constructor for person
     */
    public Person() {
        this.idNum = "";
        this.name = "";
        this.dob = new Date();
        this.address = "";
        this.phoneNum = "";
        this.email = "";
    }

    /**
     * Primary constructor to get person from the database
     * @param idNum
     * @param name
     * @param dob
     * @param address
     * @param phoneNum
     * @param email
     */
    public Person(String idNum, String name, Date dob, String address, String phoneNum, String email) {
        this.idNum = idNum;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    /**
     * Constructor created to create a new user
     * @param name
     * @param dob
     * @param address
     * @param phoneNum
     * @param email
     */
    public Person(String name, Date dob, String address, String phoneNum, String email) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    /**
     * Copy Constructor for person
     * @param person
     */
    public Person(Person person) {
        this.idNum = person.idNum;
        this.name = person.name;
        this.dob = person.dob;
        this.address = person.address;
        this.phoneNum = person.phoneNum;
        this.email = person.email;
    }

    /**
     *
     * Mutators and accessors for table class
     */
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
