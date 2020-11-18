package com.github.cla9.excel.reader.exception;

/**
 * The type Excel reader file exception.
 */
public class ExcelReaderFileException extends RuntimeException{
    /**
     * Instantiates a new Excel reader file exception.
     *
     * @param message the message
     */
    public ExcelReaderFileException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Excel reader file exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ExcelReaderFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
