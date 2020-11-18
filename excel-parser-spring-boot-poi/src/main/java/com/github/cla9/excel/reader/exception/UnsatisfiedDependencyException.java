package com.github.cla9.excel.reader.exception;

/**
 * The type Unsatisfied dependency exception.
 */
public class UnsatisfiedDependencyException extends RuntimeException {
    /**
     * Instantiates a new Unsatisfied dependency exception.
     */
    public UnsatisfiedDependencyException() {}

    /**
     * Instantiates a new Unsatisfied dependency exception.
     *
     * @param message the message
     */
    public UnsatisfiedDependencyException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Unsatisfied dependency exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnsatisfiedDependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
