package com.application.controller;
import com.database.server.Server;

import java.io.IOException;


public class Driver {
    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
