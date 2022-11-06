package com.application.models.tables.tables;


import com.application.models.misc.Date;
import com.application.models.misc.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "Employee")
public class Employee extends Person {
    public static final String[] types = {"Cashier", "Line Worker", "Manager", "Supervisor"};
    @Column (name = "type")
    private String type;
    @Column (name = "depCode")
    private String depCode;

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
