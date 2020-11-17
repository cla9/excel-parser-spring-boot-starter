package com.github.cla9.excel.reader.entity;

import java.util.List;

public class ExceptionRow<T> {
    private final T row;
    private final List<ErrorInfo> errors;

    public static <T> ExceptionRow.ExceptionRowBuilder<T> builder() {
        return new ExceptionRow.ExceptionRowBuilder();
    }

    public ExceptionRow(final T row, final List<ErrorInfo> errors) {
        this.row = row;
        this.errors = errors;
    }
    public T getRow() {
        return this.row;
    }
    public List<ErrorInfo> getErrors() {
        return this.errors;
    }

    public static class ExceptionRowBuilder<T> {
        private T row;
        private List<ErrorInfo> errors;

        ExceptionRowBuilder() {}
        public ExceptionRow.ExceptionRowBuilder<T> row(final T row) {
            this.row = row;
            return this;
        }
        public ExceptionRow.ExceptionRowBuilder<T> errors(final List<ErrorInfo> errors) {
            this.errors = errors;
            return this;
        }
        public ExceptionRow<T> build() {
            return new ExceptionRow(this.row, this.errors);
        }

    }
}