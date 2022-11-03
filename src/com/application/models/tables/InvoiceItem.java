package com.application.models.tables;

public class InvoiceItem {
    private String prodID;
    private int quantity;
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
