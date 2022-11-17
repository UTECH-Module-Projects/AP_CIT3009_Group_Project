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

//Package
package com.application.models.converters;

//Imported Libraries

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * <h1>Person Class</h1>
 * <p>
 * This class is designed to store the composite id for the invoice item stored in the database
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
@Embeddable
public class InvoiceItemID implements Serializable {
    /**
     * The id number of the invoice
     */
    @Column
    private int invID;

    /**
     * The id number of the product
     */
    @Column
    private String prodID;

    public InvoiceItemID() {
        this.invID = 0;
        this.prodID = "";
    }

    /**
     * Primary Constructor - Used to store all data for the invoice item id
     *
     * @param invID  the invoice ID
     * @param prodID the product
     */
    public InvoiceItemID(int invID, String prodID) {
        this.invID = invID;
        this.prodID = prodID;
    }

    /**
     * Used to compare two invoice item ids to see if they are equal
     *
     * @param o The invoice item object
     * @return Whether they are equal (true or false)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItemID that = (InvoiceItemID) o;
        return invID == that.invID && prodID.equals(that.prodID);
    }

    /**
     * The Invoice ID and Product ID as Objects, giving is a unique code
     *
     * @return The hashed code for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(invID, prodID);
    }
}
