package com.github.cla9.excel.reader.exception;

/**
 * The type No excel entity found exception.
 */
public class NoExcelConverterFoundException extends RuntimeException{
    /**
     * Instantiates a new No excel entity found exception.
     */
    public NoExcelConverterFoundException() { this("Converter doesn't exist"); }

    /**
     * Instantiates a new No excel entity found exception.
     *
     * @param message the message
     */
    public NoExcelConverterFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new No excel entity found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoExcelConverterFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
