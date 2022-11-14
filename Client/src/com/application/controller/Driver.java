/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 *
 */

//Package
package com.application.controller;

//Imported Libraries
import com.application.generic.SQLCond;
import com.application.generic.SQLCondBuilder;
import com.application.generic.SQLType;
import com.application.generic.TableList;
import com.application.models.tables.Customer;
import com.application.models.tables.Department;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.database.client.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Main Driver Class</h1>
 * <p>
 *     This Class is designed create and run the client
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public class Driver {
    public static void main(String[] args) {
        try {
            Client client = new Client("rush");
            TableList<Invoice, Integer> invoices = new TableList<>(Invoice.class, client.findMatchAll("Invoice", new SQLCondBuilder("customer.idNum", SQLCond.EQ, "gb0zwIcK", SQLType.TEXT)), Department.headers);
            invoices.forEach(inv -> System.out.println(Arrays.toString(inv.toArray())));
            client.closeConnection();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
