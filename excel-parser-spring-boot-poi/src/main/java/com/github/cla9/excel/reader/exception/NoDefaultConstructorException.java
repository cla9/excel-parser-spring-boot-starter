package com.github.cla9.excel.reader.exception;

public class NoDefaultConstructorException extends RuntimeException {
    public NoDefaultConstructorException() {this("ExcelBody Entity must have default constructor");}
    public NoDefaultConstructorException(String message) {super(message);}
    public NoDefaultConstructorException(String message, Throwable cause) { super(message, cause); }
}
