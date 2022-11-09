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
package com.application.controller;
import com.database.server.Server;

public class Driver {
    public static void main(String[] args) {
        try {
            new Server();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
