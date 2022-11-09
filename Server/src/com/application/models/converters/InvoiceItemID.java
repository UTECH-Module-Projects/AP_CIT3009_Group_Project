package com.application.models.converters;

import com.sun.istack.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class InvoiceItemID implements Serializable {
    private int invID;
    private String prodID;

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

    @Override
    public int hashCode() {
        return Objects.hash(invID, prodID);
    }
}
