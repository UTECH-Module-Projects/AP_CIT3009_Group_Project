/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patterson  2008034
 *
 */

//Package
package com.application.utilities;

/**
 * <h1>Utilities Class</h1>
 * <p>
 * This Class is designed to perform generic data operations such as generating unique value, checking if a value is unique, and checking if a value is null.
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
* */
public class Utilities {

    /**
     * Generates a random unique Integer using an array of Integers.
     * @param arr The array of integers to test
     * @param len The length that the integer should be
     * @return The unique integer
     */
    public static int generateUniqueInt(Integer[] arr, int len) {
        //If the length is invalid
        if (len < 1 || len > String.valueOf(Integer.MAX_VALUE).length()) return 0;

        //Indefinite loop until integer is unique
        do {
            //Generates a random integer between 10^(len-1) and 10^(len)-1
            int val = (int) (Math.random() * (Math.pow(10, len) - Math.pow(10, len-1)) + Math.pow(10, len-1));

            //If the integer is unique
            if (isUniqueInt(arr, val)) return val;
        } while (true);
    }

    /**
     * Generates a random unique String using an array of Strings.
     * @param arr The array of strings to test
     * @param len The length that the strings should be
     * @return The unique strings
     */
    public static String generateUniqueIDString(String[] arr, int len) {
        //The string of all valid characters the string may have
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "1234567890";

        //If the length is invalid
        if (len < 1) return "";

        //Indefinite loop until string is unique
        do {
            StringBuilder str = new StringBuilder();

            //Get len number of random characters and store them in a string
            for (int i = 0; i < len; i++)
                str.append(validChars.charAt((int) (validChars.length() * Math.random())));

            //If the string is unique
            if (isUniqueString(arr, str.toString())) return str.toString();
        } while (true);
    }

    /**
     * Checks if a String is unique using a list of Strings
     * @param arr The array of strings to test
     * @param str The string to test
     * @return Whether the string is unique (true or false)
     */
    public static boolean isUniqueString(String[] arr, String str) {
        for (String id : arr)
            if (id.equals(str)) return false;
        return true;
    }

    /**
     * Checks if a String is unique using a list of Strings
     * @param arr The array of integers to test
     * @param val The integer to test
     * @return Whether the integer is unique (true or false)
     */
    public static boolean isUniqueInt(Integer[] arr, int val) {
        for (int id : arr)
            if (id == val) return false;
        return true;
    }

    /**
     * Checks if an Object is null
     * @param obj The object to test
     * @return Whether the object is null (true or false)
     */
    public static String checkNull(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
}
