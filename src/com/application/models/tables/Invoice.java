package com.application.models.tables;

import com.application.models.Date;

import java.util.ArrayList;

public class Invoice {
    private int idNum;
    private Date billDate;
    private String empID;
    private String custID;
    private double total;

    public Invoice() {
        this.idNum = 0;
        this.billDate = new Date();
        this.empID = "";
        this.custID = "";
        this.total = 0.0f;
    }

    public Invoice(int idNum, Date billDate, String empID, String custID, double total) {
        this.idNum = idNum;
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.total = total;
    }

    public int getIdNum() {
        return idNum;
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
