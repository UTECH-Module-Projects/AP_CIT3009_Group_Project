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

import com.application.view.ClientApp;

/**
 * <h1>Main Driver Class</h1>
 * <p>
 * This Class is designed create and run the server
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
            new ClientApp();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
