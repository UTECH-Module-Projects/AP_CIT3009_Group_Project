package com.application.models.tables;

import com.application.models.converters.InvoiceItemID;
import jakarta.persistence.*;

@Entity
@Table (name = "InvoiceItem")
@IdClass(InvoiceItemID.class)
public class InvoiceItem {
    @Id
    private int invID;
    @Id
    private String prodID;
    @Column (name = "quantity")
    private int quantity;
    @Column (name = "unitPrice")
    private float unitPrice;

    public InvoiceItem() {
        this.prodID = "";
        this.quantity = 0;
        this.unitPrice = 0.0f;
    }

    public InvoiceItem(String prodID, int quantity, float unitPrice) {
        this.prodID = prodID;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
