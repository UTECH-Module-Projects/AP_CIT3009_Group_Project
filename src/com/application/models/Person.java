package com.application.models;

public class Person {
    private String idNum;
    private String name;
    private Date dob;
    private String address;
    private String phoneNum;
    private String email;

    public Person() {
        this.idNum = "";
        this.name = "";
        this.dob = new Date();
        this.address = "";
        this.phoneNum = "";
        this.email = "";
    }

    public Person(String idNum, String name, Date dob, String address, String phoneNum, String email) {
        this.idNum = idNum;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
