package com.application.models.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table (name = "Product")
public class Product implements Serializable {
    @Id
    @Column (name = "idNum")
    private String idNum;
    @Column (name = "name")
    private String name;
    @Column (name = "shDesc")
    private String shDesc;
    @Column (name = "loDesc")
    private String loDesc;
    @Column (name = "stock")
    private int stock;
    @Column (name = "price")
    private double price;

    public Product() {
        this.idNum = "";
        this.name = "";
        this.shDesc = "";
        this.loDesc = "";
        this.stock = 0;
        this.price = 0.0f;
    }

    public Product(String idNum, String name, String shDesc, String loDesc, int stock, double price) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idNum='" + idNum + '\'' +
                ", name='" + name + '\'' +
                ", shDesc='" + shDesc + '\'' +
                ", loDesc='" + loDesc + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
