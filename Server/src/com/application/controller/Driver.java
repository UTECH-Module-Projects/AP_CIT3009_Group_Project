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
