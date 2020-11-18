package com.github.cla9.excel.reader.exception;

/**
 * The type Invalid header exception.
 */
public class InvalidHeaderException extends RuntimeException{
    /**
     * Instantiates a new Invalid header exception.
     */
    public InvalidHeaderException() {
        this("ExcelColumn annotation should be attached or not.");
    }

    /**
     * Instantiates a new Invalid header exception.
     *
     * @param message the message
     */
    public InvalidHeaderException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid header exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidHeaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
