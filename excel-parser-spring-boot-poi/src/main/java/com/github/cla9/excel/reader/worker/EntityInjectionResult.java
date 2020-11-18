package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ErrorInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * The type Entity injection result.
 *
 * @param <T> the type parameter
 */
public class EntityInjectionResult<T> {
    private final T object;
    private final List<ErrorInfo> exceptions;

    /**
     * Gets object.
     *
     * @return the object
     */
    public T getObject() {
        return this.object;
    }

    /**
     * Gets exceptions.
     *
     * @return the exceptions
     */
    public List<ErrorInfo> getExceptions() {
        return this.exceptions;
    }

    /**
     * Instantiates a new Entity injection result.
     *
     * @param object     the object
     * @param exceptions the exceptions
     */
    public EntityInjectionResult(final T object, final List<ErrorInfo> exceptions) {
        this.object = object;
        this.exceptions = exceptions;
    }
}
