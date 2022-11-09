package com.application.models.tables;

import com.application.models.misc.Date;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * <h1>Person Class</h1>
 * <p>
 * This is the Invoice class
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patternson
 * @version 1.0
 * */

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

    /**
     * Primary Constructor for Invoice
     * @param idNum
     * @param billDate
     * @param empID
     * @param custID
     * @param discount
     * @param total
     */
    public Invoice(int idNum, Date billDate, String empID, String custID, double discount, double total) {
        this.idNum = idNum;
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.discount = discount;
        this.total = total;
    }

    public Invoice(Date billDate, String empID, String custID, double discount, double total) {
        this.billDate = billDate;
        this.empID = empID;
        this.custID = custID;
        this.discount = discount;
        this.total = total;
    }

    /**
     *
     * Mutators and accessors for table class
     */
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

    /**
     * Adds items to arraylist
     * @param item
     */
    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }

    /**
     * Removes items form arraylist
     * @param item
     */
    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.setInvoice(null);
    }

    /**
     * 
     * @return
     */
    public String[] toArray() {
        return new String[]{String.valueOf(this.idNum), this.billDate.toString(), this.empID, this.custID, String.valueOf(this.items.size()), String.format("$%.2f", this.discount), String.format("$%.2f", this.total)};
    }
}
