package com.application.generic;

import java.io.Serializable;

public record SQLCondBuilder(String field, SQLCond condition, Object val) implements Serializable {

    @Override
    public String toString() {
        return switch (condition) {
            case EQ -> field + " = " + val;
            case NEQ -> field + " != " + val;
            case GT -> field + " > " + val;
            case GE -> field + " >= " + val;
            case LT -> field + " < " + val;
            case LE -> field + " <= " + val;
            case LIKE -> field + " LIKE '" + val + "'";
        };
    }
}
