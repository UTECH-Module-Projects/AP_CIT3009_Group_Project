package com.application.dao.generic;

import com.application.utilities.Utilities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.core.util.Assert;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

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
        return entityManager.createQuery("SELECT x." + field + " FROM" + tClass.getSimpleName() + " x").getResultList();
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

    public Object genID(int length) throws HibernateException, ClassCastException {
        List<ID> ids = getIDs();

        switch (ids.get(0).getClass().getSimpleName()) {
            case "String" -> {
                List<String> strIds = ids.stream().map(idNum -> (String) idNum).toList();
                return Utilities.generateUniqueIDString(strIds.toArray(new String[0]), length);
            }
            case "Integer" -> {
                List<Integer> intIds = ids.stream().map(idNum -> (Integer) idNum).toList();
                return Utilities.generateUniqueIDInt(intIds.toArray(new Integer[0]), length);
            }
            default -> throw new HibernateException("Invalid ID Type!");
        }
    }
}
