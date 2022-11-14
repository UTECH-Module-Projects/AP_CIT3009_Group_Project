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
import com.application.utilities.Utilities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serializable;

/**
 * <h1>Person Class</h1>
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
@Table (name = "Department")
public class Department implements Serializable, DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the department table of the application
     */
    public static final String[] headers = {"Code", "Department Name"};

    /**
     * Stores the order of fields of departments in the table
     */
    public static final int ID_NUM = 0;
    public static final int NAME = 1;

    /**
     * {@link String} - The Department Code
     */
    @Id
    @Column (name = "idNum")
    private final String idNum;

    /**
     * {@link String} - The Name of the Department
     */
    @Column (name = "name")
    private final String name;

    /**
     * Default Constructor
     */
    public Department() {
        this.idNum = "";
        this.name = "";
    }

    /**
     * Primary Constructor - Used to store all data for the department
     *
     * @param idNum The Department Code
     * @param name  The Name of the Department
     */
    public Department(String idNum, String name) {
        this.idNum = idNum;
        this.name = name;
    }

    /**
     * Constructor Accepts zero or multiple field arguments to store into array
     *
     * @param fields The field attributes of the department
     */
    public Department(String[] fields) {
        this.idNum = fields[0];
        this.name = fields[1];
    }

    @Override
    public boolean isValid() {
        return !Utilities.isEmpty(idNum, name);
    }

    /**
     * Converts Department Object to a String Array Format for Table Printing
     *
     * @return The department object in string array format
     */
    public String[] toArray() {
        return new String[]{idNum, name};
    }
}
