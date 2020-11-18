package com.github.cla9.excel.reader.exception;

/**
 * The type Duplication data row pos exception.
 */
public class DuplicationDataRowPosException extends RuntimeException{
    /**
     * Instantiates a new Duplication data row pos exception.
     */
    public DuplicationDataRowPosException() {
        this("Either dataRowPos or dataRange field must be entered.");
    }

    /**
     * Instantiates a new Duplication data row pos exception.
     *
     * @param message the message
     */
    public DuplicationDataRowPosException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Duplication data row pos exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DuplicationDataRowPosException(String message, Throwable cause) {
        super(message, cause);
    }
}
