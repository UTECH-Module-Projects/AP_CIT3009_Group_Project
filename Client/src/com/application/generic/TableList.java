package com.application.generic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

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
    public TableList<T, ID> refreshList(Collection<Object> c) {
        this.clear();
        this.addAll(c.parallelStream().map(tClass::cast).toList());
        return this;
    }

    public String[][] to2DArray() {
        return this.parallelStream().map(DBTable::toArray).toList().toArray(new String[0][0]);
    }

    public int findEntity(ID idNum) {
        for (int i = 0; i < this.size(); i++)
            if (this.get(i).getIdNum().equals(idNum))
                return i;
        return -1;
    }

    public Optional<T> reduce(BinaryOperator<T> a) {
        return this.parallelStream().reduce(a);
    }

    public T reduce(T i, BinaryOperator<T> a) {
        return this.parallelStream().reduce(i, a);
    }

    public <U> U reduce(U i, BiFunction<U, ? super T, U> a, BinaryOperator<U> c) {
        return this.parallelStream().reduce(i, a, c);
    }

    public Stream<T> sorted() {
        return this.parallelStream().sorted();
    }

    public Stream<T> sorted(Comparator<? super T> c) {
        return this.parallelStream().sorted(c);
    }
}
