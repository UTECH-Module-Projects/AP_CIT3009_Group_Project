package com.application.utilities;

public class Utilities {
    public static int generateUniqueIDInt(Integer[] ids, int length) {
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
        System.out.println("Hello :C");

        if (length < 1) return "";

        do {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < length; i++)
                str.append(validChars.charAt((int) (validChars.length() * Math.random())));
            if (isUniqueString(ids, str.toString())) {
                System.out.println(str);
                return str.toString();
            }
        } while (true);
    }

    public static boolean isUniqueString(String[] ids, String key) {
        for (String id : ids)
            if (id.equals(key)) return false;
        return true;
    }

    public static boolean isUniqueInt(Integer[] ids, int key) {
        for (int id : ids)
            if (id == key) return false;
        return true;
    }

    public static String checkNull(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
}
