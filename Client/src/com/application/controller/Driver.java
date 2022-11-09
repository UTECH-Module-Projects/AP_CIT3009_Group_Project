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
import com.application.models.tables.Customer;
import com.database.client.Client;

import java.util.Arrays;

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
            Client client = new Client("test");
            Customer cust = (Customer) client.findMatch("Customer", new SQLCondBuilder("name", SQLCond.LIKE, "%r%"));
            if (cust == null) System.out.println("null");
            else System.out.println(Arrays.toString(cust.toArray()));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
