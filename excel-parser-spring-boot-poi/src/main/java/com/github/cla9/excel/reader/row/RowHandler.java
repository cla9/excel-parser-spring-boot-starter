package com.github.cla9.excel.reader.row;

/**
 * The interface Row handler.
 *
 * @param <T> the type parameter
 */
public interface RowHandler<T> {
    /**
     * Sets row.
     *
     * @param row the row
     */
    void setRow(T row);

    /**
     * Gets value.
     *
     * @param i the
     * @return the value
     */
    String getValue(int i);
}
