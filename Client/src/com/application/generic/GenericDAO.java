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

//Package
package com.application.generic;

//Imported Libraries

import java.io.Serializable;
import java.util.List;

/**
 * <h1>Generic Data Access Object Class</h1>
 * <p>
 * This Class is designed to convert time between the database and the object
 * </p>
 *
 * @param <T>
 * @param <ID>
 *
 * @author Gabrielle Johnson
 * @author Jazmin Hayles
 * @author Rushawn White
 * @author Barrignton Patterson
 * @version 1.0
 */
public interface GenericDAO<T, ID extends Serializable> {
    void create(T entity);
    void update(T entity);
    void delete(ID id);
    T get(ID id);
    List<Object> getColumn(String field);
    List<T> getAll();
    List<ID> getIDs();
    Object genID(int length);

    T findMatch(SQLCondBuilder...conditions);
    List<T> findMatchAll(SQLCondBuilder...conditions);
}
