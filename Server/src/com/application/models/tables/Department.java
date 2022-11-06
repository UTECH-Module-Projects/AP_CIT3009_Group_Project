package com.application.models.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table (name = "Department")
public class Department implements Serializable {
    @Id
    @Column (name = "idNum")
    public final String idNum;
    @Column (name = "name")
    public final String name;

    public Department(String idNum, String name) {
        this.idNum = idNum;
        this.name = name;
    }
}
