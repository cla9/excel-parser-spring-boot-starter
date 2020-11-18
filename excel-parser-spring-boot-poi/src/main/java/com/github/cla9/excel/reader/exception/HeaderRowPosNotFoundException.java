package com.github.cla9.excel.reader.exception;

/**
 * The type Header row pos not found exception.
 */
public class HeaderRowPosNotFoundException extends RuntimeException{
    /**
     * Instantiates a new Header row pos not found exception.
     */
    public HeaderRowPosNotFoundException() { this("Either headerRowPos or headerRange field must be entered.");}

    /**
     * Instantiates a new Header row pos not found exception.
     *
     * @param message the message
     */
    public HeaderRowPosNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Header row pos not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public HeaderRowPosNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
