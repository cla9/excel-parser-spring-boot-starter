package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.annotation.ExcelColumn;
import com.github.cla9.excel.reader.annotation.ExcelEmbedded;
import com.github.cla9.excel.reader.annotation.Merge;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * The type Entity instantiator source.
 */
public class EntityInstantiatorSource implements EntitySource {
    @Override
    public boolean isCreationTargetField(Field field) {
        return Stream.of(ExcelEmbedded.class, Merge.class)
                .anyMatch(field::isAnnotationPresent);
    }

    @Override
    public boolean isCandidate(Field field) {
        return Stream.of(ExcelColumn.class,ExcelEmbedded.class, Merge.class)
                .anyMatch(field::isAnnotationPresent);
    }

    @Override
    public boolean isInjectionFields(Field field) {
        return field.isAnnotationPresent(ExcelColumn.class);
    }
}
