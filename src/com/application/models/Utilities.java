package com.application.models;


import java.util.Objects;

public class Utilities {
    public static int generateUniqueIDInt(int[] ids, int length) {
        if (length < 1) return 0;
        do {
            int id = (int) (Math.random() * (Math.pow(10, length) - Math.pow(10, length-1)) + Math.pow(10, length-1));;
            if (isUniqueInt(ids, id)) return id;
        } while (true);
    }

    public static String generateUniqueIDString(String[] ids, int length) {
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "1234567890";

        if (length < 1) return "";

        do {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < length; i++)
                str.append(validChars.charAt((int) (validChars.length() * Math.random())));
            if (isUniqueString(ids, str.toString())) return str.toString();
        } while (true);
    }

    public static boolean isUniqueString(String[] ids, String key) {
        for (String id : ids)
            if (id.equals(key)) return false;
        return true;
    }

    public static boolean isUniqueInt(int[] ids, int key) {
        for (int id : ids)
            if (id == key) return false;
        return true;
    }
}
