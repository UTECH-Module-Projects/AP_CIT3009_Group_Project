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

package com.application.utilities;
/**
 * <h1>Utilities Class</h1>
 * <p>
 * This Class is designed to generate unique iDs for the database
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patternson
 * @version 1.0
* */
public class Utilities {

    /**
     *  With this, a random integer is created for the special Id.
     * @param ids
     * @param length
     * @return
     */
    public static int generateUniqueIDInt(Integer[] ids, int length) {
        if (length < 1) return 0;
        do {
            int id = (int) (Math.random() * (Math.pow(10, length) - Math.pow(10, length-1)) + Math.pow(10, length-1));;
            if (isUniqueInt(ids, id)) return id;
        } while (true);
    }

    /**
     *  Generate the random ID number
     * @param ids 
     * @param length
     * @return
     */
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

    /**
     *  Checks if String is Unique
     * @param ids
     * @param key
     * @return
     */
    public static boolean isUniqueString(String[] ids, String key) {
        for (String id : ids)
            if (id.equals(key)) return false;
        return true;
    }

    /**
     *  Checks if integer is Unique
     * @param ids
     * @param key
     * @return
     */
    public static boolean isUniqueInt(Integer[] ids, int key) {
        for (int id : ids)
            if (id == key) return false;
        return true;
    }

    /**
     * Checks if Object is null
     * @param obj
     * @return
     */
    public static String checkNull(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }
}
