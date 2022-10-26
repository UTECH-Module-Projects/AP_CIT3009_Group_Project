package com.application.controller;

import com.application.models.Customer;
import com.application.models.Date;
import com.application.models.Utilities;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        Customer cust1 = new Customer("John Brown", new Date(22, 2, 2022), "2 Primrose Close, Portmore Pines. Portmore St.Catherine", "(876) 358-8661", "rushwhite29@gmail.com");
        Customer cust2 = new Customer("John Brown", new Date(22, 2, 2022), "2 Primrose Close, Portmore Pines. Portmore St.Catherine", "(876) 358-8661", "rushwhite29@gmail.com");
        Customer cust3 = new Customer("John Brown", new Date(22, 2, 2022), "2 Primrose Close, Portmore Pines. Portmore St.Catherine", "(876) 358-8661", "rushwhite29@gmail.com");

        cust1.create();
        cust2.create();
        cust3.create();
    }
}
