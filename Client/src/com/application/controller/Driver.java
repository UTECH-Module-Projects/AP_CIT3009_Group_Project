package com.application.controller;
import com.application.models.misc.Date;
import com.application.models.tables.Customer;
import com.application.models.tables.Employee;
import com.database.client.Client;

import java.io.IOException;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        try {
            Client client = new Client();

            client.getAll("Customer").forEach(System.out::println);
            client.closeConnection();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
