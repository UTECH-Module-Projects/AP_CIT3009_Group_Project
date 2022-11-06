package com.application.models.tables;

import com.application.models.converters.InvoiceItemID;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table (name = "InvoiceItem")
@IdClass(InvoiceItemID.class)
public class InvoiceItem implements Serializable {
    @Id
    @Column (name = "invID")
    private int invID;
    @Id
    @Column (name = "prodID")
    private String prodID;

    @Column (name = "quantity")
    private int quantity;
    @Column (name = "unitPrice")
    private double unitPrice;

    public InvoiceItem() {
        this.quantity = 0;
        this.unitPrice = 0.0f;
    }

    public InvoiceItem(int invID, String prodID, int quantity, double unitPrice) {
        this.invID = invID;
        this.prodID = prodID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getInvID() {
        return invID;
    }

    public void setInvID(int invID) {
        this.invID = invID;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
