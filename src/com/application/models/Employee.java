package com.application.models;

public class Employee extends Person {
    private String type;
    private String depCode;

    public Employee() {
        super();
        this.type = "";
        this.depCode = "";
    }

    public Employee(String idNum, String name, Date dob, String address, String phoneNum, String email, String type, String depCode) {
        super(idNum, name, dob, address, phoneNum, email);
        this.type = type;
        this.depCode = depCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }
}
