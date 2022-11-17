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
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;

/**
 * <h1>Employee Class</h1>
 * <p>
 * This Class is designed to store the entity record of a Employee which is stored in the database.
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
@Table(name = "Employee")
public class Employee extends Person implements DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the employee table of the application
     */
    public static final String[] headers = {"ID Number", "Full Name", "Date of Birth", "Address", "Phone Number", "Email", "Type", "Department"};

    public static final int idLength = 8;

    /**
     * Stores the order of fields of employees in the table
     */
    public static final int ID_NUM = 0;
    public static final int NAME = 1;
    public static final int DOB = 2;
    public static final int ADDRESS = 3;
    public static final int PHONE_NUM = 4;
    public static final int EMAIL = 5;
    public static final int TYPE = 6;
    public static final int DEP_CODE = 7;

    /**
     * {@link String[]} Used to store the types of employee
     */
    public static final String[] types = {"Cashier", "Line Worker", "Manager", "Supervisor", "Admin"};

    /**
     * {@link String} - The type of employee
     */
    @Column(name = "type")
    private String type;

    /**
     * {@link Department} - The department of the employee
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "depCode")
    private Department department;

    /**
     * Default Constructor
     */
    public Employee() {
        super();
        this.type = "";
        this.department = new Department();
    }

    /**
     * Primary Constructor - Used to store all data for the employee
     *
     * @param idNum    The ID Number
     * @param name     The Full Name
     * @param dob      The Date of Birth
     * @param address  The Home Address
     * @param phoneNum The Telephone Number
     * @param email    The Email Address
     * @param type     The type of employee
     * @param department  The department of the employee
     */
    public Employee(String idNum, String name, EntityDate dob, String address, String phoneNum, String email, String type, Department department) {
        super(idNum, name, dob, address, phoneNum, email);
        this.type = type;
        this.department = department;
    }

    /**
     * Constructor Used to store all data for the employee from an array of fields
     *
     * @param fields The string array of attributes for the employee
     */
    public Employee(String[] fields) {
        super(fields[0], fields[1], new EntityDate(fields[2].split("-")), fields[3], fields[4], fields[5]);
        this.type = fields[6];
        this.department = new Department(fields[7], fields[8]);
    }

    /**
     * Checks if the Employee object is valid to be sent to the database
     *
     * @return Whether the employee object is valid (true or false)
     */
    public boolean isValid() throws ParseException {
        return !Utilities.isEmpty(idNum, name, dob, address, phoneNum, email, type, department) && getAge() >= 18;
    }

    /**
     * Converts Employee Object to a String Array Format for Table Printing
     *
     * @return The employee object in string array format
     */
    public String[] toArray() {
        return new String[]{idNum, name, Utilities.checkNull(dob), address, phoneNum, email, type, department.getName()};
    }

    /**
     * Returns the object as a Table Array
     * @return table array
     */
    public Object[] toTableArray() {
        return new Object[]{idNum, name, Utilities.checkNull(dob), address, phoneNum, email, type, department.getName()};
    }
}
