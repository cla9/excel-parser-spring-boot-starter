package com.github.cla9.excel.reader.exception;

public class NoExcelEntityFoundException extends RuntimeException{
    public NoExcelEntityFoundException() {
        this("Target class must have an ExcelBody annotation.");
    }
    public NoExcelEntityFoundException(String message) {
        super(message);
    }
    public NoExcelEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
