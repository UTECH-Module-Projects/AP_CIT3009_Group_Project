package com.application.models;

public class Department {
    private String idNum;
    private String name;

    public Department() {
        this.idNum = "";
        this.name = "";
    }

    public Department(String idNum, String name) {
        this.idNum = idNum;
        this.name = name;
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
}
