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
package com.application.models.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
 * @author Barrignton Patternson
 * @version 1.0
 * */

@Entity
@Table (name = "Department")
public class Department implements Serializable {

    /**
     * Attributes:
     * idNum
     * name
     */
    @Id
    @Column (name = "idNum")
    public final String idNum;
    @Column (name = "name")
    public final String name;

    /**
     * Primary Constructor of the Department Class
     * @param idNum
     * @param name
     */
    public Department(String idNum, String name) {
        this.idNum = idNum;
        this.name = name;
    }

    public Department(String ...fields) {
        this.idNum = fields[0];
        this.name = fields[1];
    }
}
