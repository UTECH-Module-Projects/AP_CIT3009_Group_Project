package com.application.models.converters;

import com.sun.istack.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

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
 * @author Barrignton Patternson
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
