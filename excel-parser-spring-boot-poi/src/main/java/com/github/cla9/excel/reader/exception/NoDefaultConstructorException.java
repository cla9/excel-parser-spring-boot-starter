package com.github.cla9.excel.reader.exception;

/**
 * The type No default constructor exception.
 */
public class NoDefaultConstructorException extends RuntimeException {
    /**
     * Instantiates a new No default constructor exception.
     */
    public NoDefaultConstructorException() {this("ExcelBody Entity must have default constructor");}

    /**
     * Instantiates a new No default constructor exception.
     *
     * @param message the message
     */
    public NoDefaultConstructorException(String message) {super(message);}

    /**
     * Instantiates a new No default constructor exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoDefaultConstructorException(String message, Throwable cause) { super(message, cause); }
}
