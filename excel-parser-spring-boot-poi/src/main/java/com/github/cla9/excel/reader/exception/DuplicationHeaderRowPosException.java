package com.github.cla9.excel.reader.exception;

public class DuplicationHeaderRowPosException extends RuntimeException {
    public DuplicationHeaderRowPosException() {
        this("Either headerRowPos or headerRange field must be entered.");
    }
    public DuplicationHeaderRowPosException(String message) {
        super(message);
    }
    public DuplicationHeaderRowPosException(String message, Throwable cause) {
        super(message, cause);
    }
}
