package com.github.cla9.excel.reader.worker;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;

/**
 * The interface Entity source.
 */
public interface EntitySource {

    /**
     * Gets time factory.
     *
     * @return the time factory
     */
    AnnotationFormatterFactory<DateTimeFormat> getTimeFactory();

    /**
     * Is creation target field boolean.
     *
     * @param field the field
     * @return the boolean
     */
    boolean isCreationTargetField(final Field field);

    /**
     * Is candidate boolean.
     *
     * @param field the field
     * @return the boolean
     */
    boolean isCandidate(final Field field);

    /**
     * Is injection fields boolean.
     *
     * @param field the field
     * @return the boolean
     */
    boolean isInjectionFields(final Field field);


    /**
     * Is supported injection class boolean.
     *
     * @param clazz the clazz
     * @return the boolean
     */
    boolean isSupportedInjectionClass(Class<?> clazz);

    /**
     * Is supported date type boolean.
     *
     * @param clazz the clazz
     * @return the boolean
     */
    boolean isSupportedDateType(Class<?> clazz);
}
