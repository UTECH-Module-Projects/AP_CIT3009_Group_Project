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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serializable;

/**
 * <h1>Product Class</h1>
 * <p>
 * This Class is designed to store the entity record of a product which is stored in the database.
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
@Table (name = "Product")
public class Product implements Serializable, DBTable<String> {
    /**
     * {@link String[]} Used to store the headers to be displayed in the product table of the application
     */
    public static final String[] headers = {"ID Number", "Name", "Short Description", "Long Description", "Stock", "Total Sold", "Unit Price"};

    /**
     * Stores the order of fields of invoices in the table
     */
    public static final int ID_NUM = 0;
    public static final int NAME = 1;
    public static final int SH_DESC = 2;
    public static final int LO_DESC = 3;
    public static final int STOCK = 4;
    public static final int TOT_SOLD = 5;
    public static final int PRICE = 6;

    /**
     * {@link String} The product id number
     */
    @Id
    @Column (name = "idNum")
    private String idNum;

    /**
     * {@link String} The name of the product
     */
    @Column (name = "name")
    private String name;

    /**
     * {@link String} The short description of the product
     */
    @Column (name = "shDesc")
    private String shDesc;

    /**
     * {@link String} The long description of the product
     */
    @Column (name = "loDesc")
    private String loDesc;

    /**
     * The total stock of the product
     */
    @Column (name = "stock")
    private int stock;

    /**
     * The total stock sold of the product
     */
    @Column (name = "totSold")
    private int totSold;

    /**
     * The unit price of the product
     */
    @Column (name = "price")
    private double price;

    /**
     * Default Constructor
     */
    public Product() {
        this.idNum = "";
        this.name = "";
        this.shDesc = "";
        this.loDesc = "";
        this.stock = 0;
        this.totSold = 0;
        this.price = 0.0d;
    }

    /**
     * Primary Constructor - Used to store all data for the customer
     *
     * @param idNum     The product id number
     * @param name      The name of the product
     * @param shDesc    The short description of the product
     * @param loDesc    The long description of the product
     * @param stock     The total stock of the product
     * @param totSold   The total stock sold of the product
     * @param price     The unit price of the product
     */
    public Product(String idNum, String name, String shDesc, String loDesc, int stock, int totSold, double price) {
        this.idNum = idNum;
        this.name = name;
        this.shDesc = shDesc;
        this.loDesc = loDesc;
        this.stock = stock;
        this.totSold = totSold;
        this.price = price;
    }

    /**
     * Checks if the Product object is valid to be sent to the database
     *
     * @return Whether the product object is valid (true or false)
     */
    @Override
    public boolean isValid() {
        return !Utilities.isEmpty(idNum, name, shDesc, loDesc, price) && (price > 0);
    }

    /**
     * Converts Log Object to a String Array Format for Table Printing
     *
     * @return The log object in string array format
     */
    @Override
    public String[] toArray() {
        return new String[]{idNum, name, shDesc, loDesc, String.valueOf(stock), String.format("%.2f", price)};
    }
}
