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

    /**
     * Default Constructor
     */
    public Product() {
        this.idNum = "";
        this.name = "";
        this.shDesc = "";
        this.loDesc = "";
        this.stock = 0;
        this.price = 0.0d;
    }

    /**
     * Primary Constructor
     * @param idNum
     * @param name
     * @param shDesc - short description
     * @param loDesc - long description
     * @param stock - quantity
     * @param price
     */
    public Product(String idNum, String name, String shDesc, String loDesc, int stock, double price) {
        this.idNum = idNum;
        this.name = name;
        this.shDesc = shDesc;
        this.loDesc = loDesc;
        this.stock = stock;
        this.price = price;
    }

    /**
     * Accessor for product id number
     * @return id number
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * Mutator for product id number
     * @param idNum
     */
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    /**
     * Accessor for product name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for product name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessor for product short description
     * @return short description
     */
    public String getShDesc() {
        return shDesc;
    }

    /**
     * Mutator for product short description
     * @param shDesc
     */
    public void setShDesc(String shDesc) {
        this.shDesc = shDesc;
    }

    /**
     * Accessor for product long description
     * @return long description
     */
    public String getLoDesc() {
        return loDesc;
    }

    /**
     * Mutator for product long description
     * @param loDesc
     */
    public void setLoDesc(String loDesc) {
        this.loDesc = loDesc;
    }

    /**
     * Accessor for product quantity
     * @return id number
     */
    public int getStock() {
        return stock;
    }

    /**
     * Mutator for product stock quantity
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Accessor for product price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Mutator for product price
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     *
     * @return a string of all product class variables
     */
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
