package com.application.generic;

import java.io.Serializable;

public record SQLCondBuilder(String field, SQLCond condition, Object val, SQLType type) implements Serializable {

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
