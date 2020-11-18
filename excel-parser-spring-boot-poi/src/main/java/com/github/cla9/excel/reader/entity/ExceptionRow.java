package com.github.cla9.excel.reader.entity;

import java.util.List;

/**
 * The type Exception row.
 *
 * @param <T> the type parameter
 */
public class ExceptionRow<T> {
    private final T row;
    private final List<ErrorInfo> errors;

    /**
     * Builder exception row . exception row builder.
     *
     * @param <T> the type parameter
     * @return the exception row . exception row builder
     */
    public static <T> ExceptionRow.ExceptionRowBuilder<T> builder() {
        return new ExceptionRow.ExceptionRowBuilder();
    }

    /**
     * Instantiates a new Exception row.
     *
     * @param row    the row
     * @param errors the errors
     */
    public ExceptionRow(final T row, final List<ErrorInfo> errors) {
        this.row = row;
        this.errors = errors;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public T getRow() {
        return this.row;
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List<ErrorInfo> getErrors() {
        return this.errors;
    }

    /**
     * The type Exception row builder.
     *
     * @param <T> the type parameter
     */
    public static class ExceptionRowBuilder<T> {
        private T row;
        private List<ErrorInfo> errors;

        /**
         * Instantiates a new Exception row builder.
         */
        ExceptionRowBuilder() {}

        /**
         * Row exception row . exception row builder.
         *
         * @param row the row
         * @return the exception row . exception row builder
         */
        public ExceptionRow.ExceptionRowBuilder<T> row(final T row) {
            this.row = row;
            return this;
        }

        /**
         * Errors exception row . exception row builder.
         *
         * @param errors the errors
         * @return the exception row . exception row builder
         */
        public ExceptionRow.ExceptionRowBuilder<T> errors(final List<ErrorInfo> errors) {
            this.errors = errors;
            return this;
        }

        /**
         * Build exception row.
         *
         * @return the exception row
         */
        public ExceptionRow<T> build() {
            return new ExceptionRow(this.row, this.errors);
        }

    }
}