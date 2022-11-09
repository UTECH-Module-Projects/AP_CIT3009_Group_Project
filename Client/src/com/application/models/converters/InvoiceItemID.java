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
package com.application.models.converters;

import java.io.Serializable;
import java.util.Objects;

/**
 * <h1>Person Class</h1>
 * <p>
 * This is the Person class
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 * */

public class InvoiceItemID implements Serializable {
    private int invID;
    private String prodID;

    /**
     * The primary constructor
     * @param invID the invoice ID
     * @param prodID the product ID
     */
    public InvoiceItemID(int invID, String prodID) {
        this.invID = invID;
        this.prodID = prodID;
    }


    /**
     * compares object with
     * @param o
     * @return
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
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(invID, prodID);
    }
}
