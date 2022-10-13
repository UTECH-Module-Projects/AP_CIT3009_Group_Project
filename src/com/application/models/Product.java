package com.application.models;

public class Product {
    private String idNum;
    private String name;
    private String shDesc;
    private String loDesc;
    private int stock;
    private float price;

    public Product() {
        this.idNum = "";
        this.name = "";
        this.shDesc = "";
        this.loDesc = "";
        this.stock = 0;
        this.price = 0.0f;
    }

    public Product(String idNum, String name, String shDesc, String loDesc, int stock, float price) {
        this.idNum = idNum;
        this.name = name;
        this.shDesc = shDesc;
        this.loDesc = loDesc;
        this.stock = stock;
        this.price = price;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShDesc() {
        return shDesc;
    }

    public void setShDesc(String shDesc) {
        this.shDesc = shDesc;
    }

    public String getLoDesc() {
        return loDesc;
    }

    public void setLoDesc(String loDesc) {
        this.loDesc = loDesc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
