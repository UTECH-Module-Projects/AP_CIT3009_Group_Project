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


import com.application.models.misc.Date;
import com.application.models.misc.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
/**
 * <h1>Person Class</h1>
 * <p>
 * This is the Employee class
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patternson
 * @version 1.0
 * */
@Entity
@Table (name = "Employee")
public class Employee extends Person {
    public static final String[] types = {"Cashier", "Line Worker", "Manager", "Supervisor"};
    @Column (name = "type")
    private String type;
    @Column (name = "depCode")
    private String depCode;

    /**
     * Primary constructor for Employee
     * @param idNum
     * @param name
     * @param dob
     * @param address
     * @param phoneNum
     * @param email
     * @param type
     * @param depCode
     */
    public Employee(String idNum, String name, Date dob, String address, String phoneNum, String email, String type, String depCode) {
        super(idNum, name, dob, address, phoneNum, email);
        this.type = type;
        this.depCode = depCode;
    }

    public Employee(String name, Date dob, String address, String phoneNum, String email, String type, String depCode) {
        super(name, dob, address, phoneNum, email);
        this.type = type;
        this.depCode = depCode;
    }

    /**
     *
     * Mutators and accessors for class
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    /**
     * Convert Employee Object to string
     * @return
     */
    @Override
    public String toString() {
        return "Employee{" +
                "type='" + type + '\'' +
                ", depCode='" + depCode + '\'' +
                ", idNum='" + idNum + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
