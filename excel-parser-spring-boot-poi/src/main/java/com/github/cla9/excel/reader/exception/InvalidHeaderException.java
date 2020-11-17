package com.github.cla9.excel.reader.exception;

public class InvalidHeaderException extends RuntimeException{
    public InvalidHeaderException() {
        this("ExcelColumn annotation should be attached or not.");
    }
    public InvalidHeaderException(String message) {
        super(message);
    }
    public InvalidHeaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
