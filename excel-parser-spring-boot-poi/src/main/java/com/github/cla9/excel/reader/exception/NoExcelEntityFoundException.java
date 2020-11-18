package com.github.cla9.excel.reader.exception;

/**
 * The type No excel entity found exception.
 */
public class NoExcelEntityFoundException extends RuntimeException{
    /**
     * Instantiates a new No excel entity found exception.
     */
    public NoExcelEntityFoundException() {
        this("Target class must have an ExcelBody annotation.");
    }

    /**
     * Instantiates a new No excel entity found exception.
     *
     * @param message the message
     */
    public NoExcelEntityFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new No excel entity found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoExcelEntityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
