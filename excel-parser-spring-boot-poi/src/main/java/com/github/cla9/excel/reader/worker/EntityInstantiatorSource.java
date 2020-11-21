package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.annotation.ExcelColumn;
import com.github.cla9.excel.reader.annotation.ExcelEmbedded;
import com.github.cla9.excel.reader.annotation.Merge;
import org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * The type Entity instantiator source.
 */
public class EntityInstantiatorSource implements EntitySource {
    private final Jsr310DateTimeFormatAnnotationFormatterFactory timeFactory;


    public EntityInstantiatorSource() {
        timeFactory = new Jsr310DateTimeFormatAnnotationFormatterFactory();
    }

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

    @Override
    public boolean isSupportedInjectionClass(Class<?> clazz) {
        return timeFactory.getFieldTypes().contains(clazz) || ClassUtils.isPrimitiveOrWrapper(clazz) || String.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean isSupportedDateType(Class<?> clazz) {
        return timeFactory.getFieldTypes().contains(clazz);
    }

    @Override
    public Jsr310DateTimeFormatAnnotationFormatterFactory getTimeFactory() {
        return timeFactory;
    }

}
