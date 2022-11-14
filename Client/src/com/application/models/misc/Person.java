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

//Package
package com.application.models.misc;

//Imported Libraries

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <h1>Person Class</h1>
 * <p>
 * This Class is designed to store the basic information of a Customer or Employee which is stored in the database.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Person implements Serializable {
    /**
     * {@link String} - The id number of the person
     */
    @Id
    @Column(name = "idNum")
    protected String idNum;

    /**
     * {@link String} - The name of the person
     */
    @Column(name = "name")
    protected String name;

    /**
     * {@link EntityDate} - The date of birth of the person
     */
    @Column(name = "dob")
    protected EntityDate dob;

    /**
     * {@link String} - The address of the person
     */
    @Column(name = "address")
    protected String address;

    /**
     * {@link String} - The phone number of the person
     */
    @Column(name = "phoneNum")
    protected String phoneNum;

    /**
     * {@link String} - The email of the person
     */
    @Column(name = "email")
    protected String email;

    /**
     * Default constructor for person
     */
    public Person() {
        this.idNum = "";
        this.name = "";
        this.dob = null;
        this.address = "";
        this.phoneNum = "";
        this.email = "";
    }

    /**
     * Primary Constructor - Used to store all data for the person
     *
     * @param idNum    The id number of the person
     * @param name     The name of the person
     * @param dob      The date of birth of the person
     * @param address  The address of the person
     * @param phoneNum The phone number of the person
     * @param email    The email of the person
     */
    public Person(String idNum, String name, EntityDate dob, String address, String phoneNum, String email) {
        this.idNum = idNum;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    /**
     * Calculates the age of a person using their date of birth
     *
     * @return The age of the person
     */
    public int getAge() {
        return Math.round(dob.dateDiff(EntityDate.today()) / EntityDate.daysInYear);
    }
}
