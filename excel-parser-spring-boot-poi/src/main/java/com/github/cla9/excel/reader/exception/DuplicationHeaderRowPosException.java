package com.github.cla9.excel.reader.exception;

/**
 * The type Duplication header row pos exception.
 */
public class DuplicationHeaderRowPosException extends RuntimeException {
    /**
     * Instantiates a new Duplication header row pos exception.
     */
    public DuplicationHeaderRowPosException() {
        this("Either headerRowPos or headerRange field must be entered.");
    }

    /**
     * Instantiates a new Duplication header row pos exception.
     *
     * @param message the message
     */
    public DuplicationHeaderRowPosException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Duplication header row pos exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DuplicationHeaderRowPosException(String message, Throwable cause) {
        super(message, cause);
    }
}
