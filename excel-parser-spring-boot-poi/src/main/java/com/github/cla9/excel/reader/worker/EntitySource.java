package com.github.cla9.excel.reader.worker;

import java.lang.reflect.Field;

public interface EntitySource {
    boolean isCreationTargetField(final Field field);
    boolean isCandidate(final Field field);
    boolean isInjectionFields(final Field field);
}
