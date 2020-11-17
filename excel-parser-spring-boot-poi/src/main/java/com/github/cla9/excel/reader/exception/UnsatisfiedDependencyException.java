package com.github.cla9.excel.reader.exception;

public class UnsatisfiedDependencyException extends RuntimeException {
    public UnsatisfiedDependencyException() {}
    public UnsatisfiedDependencyException(String message) {
        super(message);
    }
    public UnsatisfiedDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
