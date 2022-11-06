package com.application.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {
    void create(T entity);
    void update(T entity);
    void delete(ID id);
    T get(ID id);
    List<T> getAll();
    List<ID> getIDs();
    Object genID(int length);
}
