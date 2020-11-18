package com.github.cla9.excel.reader.exception;

/**
 * The type Data row pos not found exception.
 */
public class DataRowPosNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Data row pos not found exception.
     */
    public DataRowPosNotFoundException() {
        this("Either dataRowPos or dataRange field must be entered.");
    }

    /**
     * Instantiates a new Data row pos not found exception.
     *
     * @param message the message
     */
    public DataRowPosNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Data row pos not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DataRowPosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
