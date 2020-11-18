package com.github.cla9.excel.reader.worker;


import com.github.cla9.excel.reader.entity.ExceptionRow;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Excel result set.
 *
 * @param <T> the type parameter
 */
public class ExcelResultSet<T> {
    private final List<T> items;
    private final List<ExceptionRow<T>> errors;

    /**
     * Instantiates a new Excel result set.
     */
    ExcelResultSet() {
        this.items = new ArrayList<>();
        this.errors = new ArrayList<>();
    }

    /**
     * Push all validated list.
     *
     * @param items the items
     */
    void pushAllValidatedList(final List<? extends T> items){
        this.items.addAll(items);
    }

    /**
     * Push invalidated list.
     *
     * @param field the field
     */
    void pushInvalidatedList(final ExceptionRow<T> field){ this.errors.add(field); }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<T> getItems() {return items;}

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<ExceptionRow<T>> getErrors() { return errors; }
}
