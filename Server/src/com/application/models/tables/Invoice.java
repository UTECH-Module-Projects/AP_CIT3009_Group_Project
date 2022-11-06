package com.application.models.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table (name = "Invoice")
public class Invoice implements Serializable {
    @Id
    @Column (name = "idNum")
    private int idNum;

    @Column (name = "billDate")
    private Date billDate;

    @Column (name = "empID")
    private String empID;

    @Column (name = "custID")
    private String custID;

    @Column (name = "total")
    private double total;

    public Invoice(int idNum, Date billDate, String empID, String custID, double total) {
        this.idNum = idNum;
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.total = total;
    }

    public Invoice(Date billDate, String empID, String custID, double total) {
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.total = total;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
