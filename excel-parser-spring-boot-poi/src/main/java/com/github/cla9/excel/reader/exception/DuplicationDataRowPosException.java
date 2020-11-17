package com.github.cla9.excel.reader.exception;

public class DuplicationDataRowPosException extends RuntimeException{
    public DuplicationDataRowPosException() {
        this("Either dataRowPos or dataRange field must be entered.");
    }
    public DuplicationDataRowPosException(String message) {
        super(message);
    }
    public DuplicationDataRowPosException(String message, Throwable cause) {
        super(message, cause);
    }
}
