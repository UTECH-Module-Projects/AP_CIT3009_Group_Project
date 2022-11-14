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
import com.application.models.misc.EntityTime;
import com.application.utilities.Utilities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Invoice Class</h1>
 * <p>
 * This Class is designed to store the entity record of a Invoice which is stored in the database.
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
@Table(name = "Invoice")
public class Invoice implements Serializable, DBTable<Integer> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the invoice table of the application
     */
    public static final String[] headers = {"ID Number", "Date", "Employee", "Customer", "Items", "Total"};

    /**
     * Stores the order of fields of invoices in the table
     */
    public static final int ID_NUM = 0;
    public static final int BILL_DATE = 1;
    public static final int BILL_TIME = 2;
    public static final int EMP_ID = 3;
    public static final int CUST_ID = 4;
    public static final int DISCOUNT = 5;
    public static final int TOTAL = 6;

    /**
     * The invoice id number (randomly generated)
     */
    @Id
    @Column(name = "idNum")
    private int idNum;

    /**
     * {@link EntityDate} The billing date of the invoice
     */
    @Column(name = "billDate")
    private EntityDate billEntityDate;

    /**
     * {@link EntityTime} The billing time of the invoice
     */
    @Column(name = "billTime")
    private EntityTime billEntityTime;

    /**
     * {@link String} The id number of the employee who completed the transaction
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custID")
    private Customer customer;

    /**
     * The total discount for the order
     */
    @Column(name = "discount")
    private double discount;

    /**
     * The overall total for the order
     */
    @Column(name = "total")
    private double total;

    @Setter
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<InvoiceItem> items = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Invoice() {
        this.idNum = 0;
        this.billEntityDate = null;
        this.billEntityTime = null;
        this.employee = null;
        this.discount = 0.0d;
        this.total = 0.0d;
    }

    /**
     * Primary Constructor - Used to store all data for the invoice
     *
     * @param idNum     The invoice number (randomly generated)
     * @param billEntityDate  The billing date of the invoice
     * @param billEntityTime  The billing time of the invoice
     * @param employee  The employee who completed the transaction
     * @param customer  The customer who placed the order
     * @param discount  The total discount for the order
     * @param total     The overall total for the order
     */
    public Invoice(int idNum, EntityDate billEntityDate, EntityTime billEntityTime, Employee employee, Customer customer, double discount, double total) {
        this.idNum = idNum;
        this.billEntityDate = billEntityDate;
        this.billEntityTime = billEntityTime;
        this.employee = employee;
        this.customer = customer;
        this.discount = discount;
        this.total = total;
    }

    @Override
    public Integer getIdNum() {
        return idNum;
    }

    @Override
    public boolean isValid() throws ParseException {
        return !Utilities.isEmpty(idNum, billEntityDate, billEntityTime, total) && (Utilities.isEmpty(employee) || employee.isValid()) && (Utilities.isEmpty(customer) || customer.isValid()) && (total > 0) && (discount >= 0);
    }

    /**
     * Converts Invoice Object to a String Array Format for Table Printing
     *
     * @return The invoice object in string array format
     */
    public String[] toArray() {
        return new String[]{String.valueOf(idNum), billEntityDate.toString(), employee.getName(), customer.getName(), String.valueOf(items.size()), String.format("$%.2f", total)};
    }
}
