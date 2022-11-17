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
package com.application.generic;

//Imported Libraries
import java.io.Serializable;

/**
 * <h1>SQL Condition Builder</h1>
 * <p>
 * This class is design
 * </p>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 * */
public record SQLCondBuilder(String field, SQLCond condition, Object val, SQLType type) implements Serializable {

    /**
     *
     * @return The switch condition is returned with the appropriate string
     */
    @Override
    public String toString() {
        String str = switch (type) {
            case TEXT -> "'" + val + "'";
            case NUM -> val.toString();
        };
        return switch (condition) {
            case EQ -> field + " = " + str;
            case NEQ -> field + " != " + str;
            case GT -> field + " > " + str;
            case GE -> field + " >= " + str;
            case LT -> field + " < " + str;
            case LE -> field + " <= " + str;
            case LIKE -> field + " LIKE " + str;
        };
    }
}
