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

import com.application.models.misc.Date;
import com.application.models.misc.Person;
import com.application.utilities.Utilities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

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
@Entity
@Table(name = "Customer")
public class Customer extends Person {
    /**
     * {@link String[]} Used to store the headers to be displayed in the customer table of the application
     */
    public static final String[] headers = {"ID Number", "Full Name", "Date of Birth", "Address", "Phone Number", "Email", "Date of Membership", "Date of Expiry"};
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
     * {@link Date} Stores the Date of Membership
     */
    @Column(name = "dom")
    private Date dom;

    /**
     * {@link Date} Stores the Date of Membership Expiry
     */
    @Column(name = "dome")
    private Date dome;

    /**
     * Default Constructor
     */
    public Customer() {
        super();
        this.isMem = false;
        this.dom = new Date();
        this.dome = new Date();
    }

    /**
     * Primary Constructor - Used to store all data for the customer
     *
     * @param idNum The ID Number
     * @param name The Full Name
     * @param dob The Date of Birth
     * @param address The Home Address
     * @param phoneNum The Telephone Number
     * @param email The Email Address
     * @param isMem Whether the Customer is a Member
     * @param dom The Date of Membership
     * @param dome The Date of Membership Expiry
     */
    public Customer(String idNum, String name, Date dob, String address, String phoneNum, String email, boolean isMem, Date dom, Date dome) {
        super(idNum, name, dob, address, phoneNum, email);
        this.isMem = isMem;
        this.dom = dom;
        this.dome = dome;
    }

    /**
     * Converts Customer Object to a String Array Format for Table Printing
     *
     * @return The customer object in string array format
     */
    public String[] toArray() {
        return new String[]{idNum, name, Utilities.checkNull(dob), address, phoneNum, email, Utilities.checkNull(dom), Utilities.checkNull(dome)};
    }
}
