package com.application.generic;

import java.text.ParseException;

public interface DBTable<ID> {
    String[] toArray();
    boolean isValid() throws ParseException;
    ID getIdNum();
}
