package com.github.cla9.excel.reader.entity;

import java.util.List;

/**
 * The type Excel row exception.
 */
public class ExcelRowException extends Exception {
    private final Object row;
    private final List<ErrorInfo> excelReaderErrorField;

    /**
     * Instantiates a new Excel row exception.
     *
     * @param row                   the row
     * @param excelReaderErrorField the excel reader error field
     */
    public ExcelRowException(final Object row, final List<ErrorInfo> excelReaderErrorField) {
        this.row = row;
        this.excelReaderErrorField = excelReaderErrorField;
    }

    /**
     * Gets row.
     *
     * @return the row
     */
    public Object getRow() {
        return this.row;
    }

    /**
     * Gets excel reader error field.
     *
     * @return the excel reader error field
     */
    public List<ErrorInfo> getExcelReaderErrorField() {
        return this.excelReaderErrorField;
    }
}
