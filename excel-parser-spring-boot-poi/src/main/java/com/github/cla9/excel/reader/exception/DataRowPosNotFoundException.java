package com.github.cla9.excel.reader.exception;

public class DataRowPosNotFoundException extends RuntimeException {
    public DataRowPosNotFoundException() {
        this("Either dataRowPos or dataRange field must be entered.");
    }
    public DataRowPosNotFoundException(String message) {
        super(message);
    }
    public DataRowPosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
