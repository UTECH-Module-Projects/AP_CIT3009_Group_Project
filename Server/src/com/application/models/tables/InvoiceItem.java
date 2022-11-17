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
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * <h1>Invoice Item Class</h1>
 * <p>
 * This Class is designed to store the entity record of a InvoiceItem which is stored in the database.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Getter
@Setter
@Entity
@Table (name = "InvoiceItem")
public class InvoiceItem implements Serializable, DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the invoiceItem table of the application
     */
    public static final String[] headers = {"Product", "Quantity", "Unit Price", "Total"};

    public static final int idLength = 12;

    /**
     * Stores the order of fields of invoice items in the table
     */
    public static final int PROD_ID = 0;
    public static final int QUANTITY = 1;
    public static final int UNIT_PRICE = 2;
    public static final int TOTAL = 3;

    @Id
    @Column(name = "idNum")
    private String idNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invID")
    private Invoice invoice;

    /**
     * {@link String} The id number of the product
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prodID")
    private Product product;

    @Column (name = "name")
    private String name;

    /**
     * The total quantity purchased
     */
    @Column (name = "quantity")
    private int quantity;

    /**
     * The unit price of the product
     */
    @Column (name = "unitPrice")
    private double unitPrice;

    /**
     * Default Constructor
     */
    public InvoiceItem() {
        this.idNum = "";
        this.invoice = null;
        this.product = null;
        this.name = "";
        this.quantity = 0;
        this.unitPrice = 0.0d;
    }

    /**
     * Primary Constructor - Used to store all data for the invoice item
     *
     * @param idNum     The id number of the invoice
     * @param product    The id number of the product
     * @param quantity  The total quantity purchased
     * @param unitPrice The unit price of the product
     */
    public InvoiceItem(String idNum, Invoice invoice, Product product, String name, int quantity, double unitPrice) {
        this.idNum = idNum;
        this.invoice = invoice;
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean isValid() {
        return !Utilities.isEmpty(idNum, invoice, product, quantity, unitPrice) && product.isValid() && (quantity > 0) && (unitPrice > 0);
    }

    /**
     * Converts InvoiceItem Object to a String Array Format for Table Printing
     *
     * @return The invoiceItem object in string array format
     */
    public String[] toArray() {
        return new String[]{name, String.valueOf(quantity), String.format("$%.2f", unitPrice), String.format("$%.2f", quantity * unitPrice)};
    }

    /**
     * Returns the object as a Table Array
     * @return table array
     */
    public Object[] toTableArray() {
        return toArray();
    }
}
