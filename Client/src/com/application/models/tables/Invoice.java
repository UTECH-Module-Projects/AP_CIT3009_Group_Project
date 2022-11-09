package com.application.models.tables;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Invoice")
public class Invoice implements Serializable {
    public static final String[] fields = {"ID Number", "Billing Date", "Employee ID", "Customer ID", "Item Count", "Discount", "Total"};
    @Id
    @Column (name = "idNum")
    private int idNum;

    @Column (name = "billDate")
    private Date billDate;

    @Column (name = "empID")
    private String empID;

    @Column (name = "custID")
    private String custID;

    @Column (name = "discount")
    private double discount;

    @Column (name = "total")
    private double total;

    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<InvoiceItem> items = new ArrayList<>();

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

    public int getIdNum() {
        return idNum;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.setInvoice(null);
    }

    public String[] toArray() {
        return new String[]{String.valueOf(this.idNum), this.billDate.toString(), this.empID, this.custID, String.valueOf(this.items.size()), String.format("$%.2f", this.discount), String.format("$%.2f", this.total)};
    }
}
