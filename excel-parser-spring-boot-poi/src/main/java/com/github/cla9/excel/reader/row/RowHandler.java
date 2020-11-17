package com.github.cla9.excel.reader.row;

public interface RowHandler<T> {
    void setRow(T row);
    String getValue(int i);
}
