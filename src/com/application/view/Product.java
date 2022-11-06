package com.application.view;

public class Product{
    private String productName;
    private String cost;
    private String shortDes;

    public Product(){
      this.productName = "hello";
      this.cost = "world";
      this.shortDes = "testing";
    }
    public Product(String productName, String cost, String shortDes) {
      this.productName = productName;
      this.cost = cost;
      this.shortDes = shortDes;
    }
    public Product (Product prodCopy){
      this.productName = prodCopy.productName;
      this.cost = prodCopy.cost;
      this.shortDes = prodCopy.shortDes;
    }
    public String getProductName() {
      return productName;
    }
    public void setProductName(String productName) {
      this.productName = productName;
    }
    public String getCost() {
      return cost;
    }
    public void setCost(String cost) {
      this.cost = cost;
    }
    public String getShortDes() {
      return shortDes;
    }
    public void setShortDes(String shortDes) {
      this.shortDes = shortDes;
    }
   @Override
   public String toString() {
        return "Product Name: "+this.productName+"\nCost: "+this.cost+"\nShort Description: "+this.shortDes;
   }
  }
