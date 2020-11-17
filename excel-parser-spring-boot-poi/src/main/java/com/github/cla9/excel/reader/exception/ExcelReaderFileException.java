package com.github.cla9.excel.reader.exception;

public class ExcelReaderFileException extends RuntimeException{
    public ExcelReaderFileException(String message) {
        super(message);
    }
    public ExcelReaderFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
