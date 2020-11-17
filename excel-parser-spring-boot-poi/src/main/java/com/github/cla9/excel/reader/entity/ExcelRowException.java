package com.github.cla9.excel.reader.entity;

import java.util.List;

public class ExcelRowException extends Exception {
    private final Object row;
    private final List<ErrorInfo> excelReaderErrorField;

    public ExcelRowException(final Object row, final List<ErrorInfo> excelReaderErrorField) {
        this.row = row;
        this.excelReaderErrorField = excelReaderErrorField;
    }

    public Object getRow() {
        return this.row;
    }
    public List<ErrorInfo> getExcelReaderErrorField() {
        return this.excelReaderErrorField;
    }
}
