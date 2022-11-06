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
        ids.forEach(System.out::println);
        Object id;

        switch (ids.get(0).getClass().getSimpleName()) {
            case "String" -> {
                List<String> strIds = new ArrayList<>();
                ids.forEach(idNum -> strIds.add((String) idNum));
                id = Utilities.generateUniqueIDString(strIds.toArray(new String[0]), length);
            }
            case "Integer" -> {
                List<Integer> intIds = new ArrayList<>();
                ids.forEach(idNum -> intIds.add((Integer) idNum));
                id = Utilities.generateUniqueIDInt(intIds.toArray(new Integer[0]), length);
            }
            default -> throw new HibernateException("Invalid ID Type!");
        }

        System.out.println(id);
        return id;
    }
}
