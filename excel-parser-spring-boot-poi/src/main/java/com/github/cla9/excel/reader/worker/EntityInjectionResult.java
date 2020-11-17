package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ErrorInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class EntityInjectionResult<T> {
    private final T object;
    private final List<ErrorInfo> exceptions;

    public T getObject() {
        return this.object;
    }
    public List<ErrorInfo> getExceptions() {
        return this.exceptions;
    }

    public EntityInjectionResult(final T object, final List<ErrorInfo> exceptions) {
        this.object = object;
        this.exceptions = exceptions;
    }
}
