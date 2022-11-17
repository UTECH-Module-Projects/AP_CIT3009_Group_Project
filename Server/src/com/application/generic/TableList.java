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
import lombok.Getter;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>Table List Class</h1>
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
@Getter
public class TableList<T extends DBTable<ID>, ID> extends ArrayList<T> {
    Class<T> tClass;
    String[] headers;

    /**
     * Primary constructor which sets upa new table list for an entity
     * @param tClass
     * @param c
     * @param headers
     */
    public TableList(Class<T> tClass, Collection<Object> c, String[] headers) {
        super(c.parallelStream().map(tClass::cast).toList());
        this.tClass = tClass;
        this.headers = headers;
    }

    /**
     *
     * @param tClass
     * @param headers
     */
    public TableList(Class<T> tClass, String[] headers) {
        super();
        this.tClass = tClass;
        this.headers = headers;
    }

    /**
     * Refresh the entire table list with new data
     * @param c
     * @return
     */
    public TableList<T, ID> refresh(Collection<Object> c) {
        this.clear();
        this.addAll(c.parallelStream().map(tClass::cast).toList());
        return this;
    }

    /**
     *
     * @param c
     * @return
     */
    public TableList<T, ID> refresh(List<T> c) {
        this.clear();
        this.addAll(c);
        return this;
    }

    /**
     *
     * @return
     */
    public String[][] to2DArray() {
        return this.parallelStream().map(DBTable::toArray).toList().toArray(new String[0][0]);
    }

    public Object[][] to2DTableArray() {
        return this.parallelStream().map(DBTable::toTableArray).toList().toArray(Object[][]::new);
    }

    /**
     *
     * @param idNum
     * @return
     */
    public int findEntity(ID idNum) {
        for (int i = 0; i < this.size(); i++)
            if (this.get(i).getIdNum().equals(idNum))
                return i;
        return -1;
    }

    /**
     *
     * @param a
     * @return
     */
    public Optional<T> reduce(BinaryOperator<T> a) {
        return this.parallelStream().reduce(a);
    }

    /**
     *
     * @param i
     * @param a
     * @return
     */
    public T reduce(T i, BinaryOperator<T> a) {
        return this.parallelStream().reduce(i, a);
    }

    /**
     *
     * @param i
     * @param a
     * @param c
     * @return
     * @param <U>
     */
    public <U> U reduce(U i, BiFunction<U, ? super T, U> a, BinaryOperator<U> c) {
        return this.parallelStream().reduce(i, a, c);
    }

    /**
     *
     * @return
     */
    public Stream<T> sorted() {
        return this.parallelStream().sorted();
    }

    /**
     *
     * @param c
     * @return
     */
    public Stream<T> sorted(Comparator<? super T> c) {
        return this.parallelStream().sorted(c);
    }

    /**
     *
     * @param predicate
     * @return
     */
    public List<T> filter(Predicate<? super T> predicate) {
        return this.parallelStream().filter(predicate).toList();
    }

    /**
     *
     * @param mapper
     * @return
     * @param <R>
     */
    public <R> List<R> map(Function<? super T, ? extends R> mapper) {
        return this.stream().map(mapper).collect(Collectors.toList());
    }
}
