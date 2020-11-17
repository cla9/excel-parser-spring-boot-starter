package com.github.cla9.excel.reader.worker;


import com.github.cla9.excel.reader.entity.ExceptionRow;

import java.util.ArrayList;
import java.util.List;


public class ExcelResultSet<T> {
    private final List<T> items;
    private final List<ExceptionRow<T>> errors;

    ExcelResultSet() {
        this.items = new ArrayList<>();
        this.errors = new ArrayList<>();
    }
    void pushAllValidatedList(final List<? extends T> items){
        this.items.addAll(items);
    }
    void pushInvalidatedList(final ExceptionRow<T> field){ this.errors.add(field); }

    public List<T> getItems() {return items;}
    public List<ExceptionRow<T>> getErrors() { return errors; }
}
