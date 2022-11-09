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
package com.application.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
    void create(T entity);
    void update(T entity);
    void delete(ID id);
    T get(ID id);
    List<Object> getColumn(String field);
    List<T> getAll();
    List<ID> getIDs();
    Object genID(int length);
}
