/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patterson  2008034
 *
 */

//Package
package com.application.models.tables;

//Imported Libraries

import com.application.generic.DBTable;
import com.application.models.misc.EntityDate;
import com.application.models.misc.Person;
import com.application.utilities.Utilities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;

/**
 * <h1>Customer Class</h1>
 * <p>
 * This Class is designed to store the entity record of a Customer which is stored in the database.
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
@Entity
@Table(name = "Customer")
public class Customer extends Person implements DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the customer table of the application
     */
    public static final String[] headers = {"ID Number", "Full Name", "Date of Birth", "Address", "Phone Number", "Email", "Is Member?", "Date of Membership", "Date of Expiry"};

    /**
     * Stores the order of fields of customers in the table
     */
    public static final int ID_NUM = 0;
    public static final int NAME = 1;
    public static final int DOB = 2;
    public static final int ADDRESS = 3;
    public static final int PHONE_NUM = 4;
    public static final int EMAIL = 5;
    public static final int IS_MEM = 6;
    public static final int DOM = 7;
    public static final int DOME = 8;

    /**
     * Used to store the length of an id number
     */
    public static final int idLength = 8;

    /**
     * Stores whether the customer is a member
     */
    @Column(name = "isMem")
    private boolean isMem;

    /**
     * {@link EntityDate} Stores the Date of Membership
     */
    @Column(name = "dom")
    private EntityDate dom;

    /**
     * {@link EntityDate} Stores the Date of Membership Expiry
     */
    @Column(name = "dome")
    private EntityDate dome;

    /**
     * Default Constructor
     */
    public Customer() {
        super();
        this.isMem = false;
        this.dom = null;
        this.dome = null;
    }

    /**
     * Primary Constructor - Used to store all data for the customer
     *
     * @param idNum    The ID Number
     * @param name     The Full Name
     * @param dob      The Date of Birth
     * @param address  The Home Address
     * @param phoneNum The Telephone Number
     * @param email    The Email Address
     * @param isMem    Whether the Customer is a Member
     * @param dom      The Date of Membership
     * @param dome     The Date of Membership Expiry
     */
    public Customer(String idNum, String name, EntityDate dob, String address, String phoneNum, String email, boolean isMem, EntityDate dom, EntityDate dome) {
        super(idNum, name, dob, address, phoneNum, email);
        this.isMem = isMem;
        this.dom = dom;
        this.dome = dome;
    }



    /**
     * Checks if the Customer object is valid to be sent to the database
     *
     * @return Whether the customer object is valid (true or false)
     */
    public boolean isValid() throws ParseException {
        return !Utilities.isEmpty(idNum, name, dob, address, phoneNum, email, isMem) && (isMem ? (!Utilities.isEmpty(dom, dome) && dom.compare(dome) == -1) : Utilities.isEmpty(dom, dome)) && getAge() >= 18;
    }

    /**
     * Converts Customer Object to a String Array Format for Table Printing
     *
     * @return The customer object in string array format
     */
    public String[] toArray() {
        return new String[]{idNum, name, Utilities.checkNull(dob), address, phoneNum, email, String.valueOf(isMem), isMem ? Utilities.checkNull(dom) : "", isMem ? Utilities.checkNull(dome) : ""};
    }
}
