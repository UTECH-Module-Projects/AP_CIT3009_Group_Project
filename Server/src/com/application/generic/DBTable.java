package com.application.generic;

import com.application.models.tables.Customer;

import java.text.ParseException;

/**
 * <h1>Database Table Interface</h1>
 * <p>
 * This class is design to get the data in an array check if it is valid and return true/false
 * and gets the Id Number if the data is converted to string properly
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 * */

/**
 *
 * @param <ID> gets ID if it is valid
 */
public interface DBTable<ID> {
    String[] toArray();
    Object[] toTableArray();
    boolean isValid() throws ParseException;
    ID getIdNum();
}
