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

import com.application.utilities.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;

public class GenericJPADAO<T, ID extends Serializable> implements GenericDAO<T, ID> {
    private final Class<T> tClass;
    private final EntityManager entityManager;

    public GenericJPADAO(Class<T> tClass, EntityManager entityManager) {
        this.tClass = tClass;
        this.entityManager = entityManager;
    }

    @Override
    public void create(T entity) throws HibernateException {
        EntityTransaction t = entityManager.getTransaction();
        t.begin();
        entityManager.persist(entity);
        t.commit();
    }

    @Override
    public void update(T entity) throws HibernateException {
        EntityTransaction t = entityManager.getTransaction();
        t.begin();
        entityManager.merge(entity);
        t.commit();
    }

    @Override
    public void delete(ID id) throws HibernateException {
        T entity = get(id);
        EntityTransaction t = entityManager.getTransaction();
        t.begin();
        entityManager.remove(entity);
        t.commit();
    }

    @Override
    @Transactional
    public T get(ID id) throws HibernateException {
        return entityManager.find(tClass, id);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Object> getColumn(String field) throws HibernateException {
        return entityManager.createQuery("SELECT x." + field + " FROM " + tClass.getSimpleName() + " x").getResultList();
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<T> getAll() throws HibernateException {
        return entityManager.createQuery("SELECT x FROM " + tClass.getSimpleName() + " x").getResultList();
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<ID> getIDs() throws HibernateException {
        return entityManager.createQuery("SELECT x.idNum FROM " + tClass.getSimpleName() + " x").getResultList();
    }

    @Override
    public Object genID(int length) throws HibernateException, ClassCastException {
        List<ID> ids = getIDs();

        switch (ids.get(0).getClass().getSimpleName()) {
            case "String" -> {
                List<String> strIds = ids.stream().map(idNum -> (String) idNum).toList();
                return Utilities.generateUniqueIDString(strIds.toArray(new String[0]), length);
            }
            case "Integer" -> {
                List<Integer> intIds = ids.stream().map(idNum -> (Integer) idNum).toList();
                return Utilities.generateUniqueInt(intIds.toArray(new Integer[0]), length);
            }
            default -> throw new HibernateException("Invalid ID Type!");
        }
    }

    private String prepSTMT(SQLCondBuilder[] conditions) {
        StringBuilder str = new StringBuilder("SELECT x FROM " + tClass.getSimpleName() + " x");

        if (conditions.length > 0) str.append(" WHERE ");

        for (int i = 0; i < conditions.length; i++) {
            str.append("x.").append(conditions[i].toString());
            if (i < (conditions.length - 1)) str.append(" AND ");
        }
        return str.toString();
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public T findMatch(SQLCondBuilder[] conditions) {
        return (T) entityManager.createQuery(prepSTMT(conditions)).setMaxResults(1).getSingleResult();
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<T> findMatchAll(SQLCondBuilder[] conditions) {
        return entityManager.createQuery(prepSTMT(conditions)).getResultList();
    }
}
