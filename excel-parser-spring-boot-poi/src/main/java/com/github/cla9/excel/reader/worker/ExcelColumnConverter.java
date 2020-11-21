package com.github.cla9.excel.reader.worker;

/**
 * The interface Excel column converter.
 *
 * @param <X> the type parameter for entity attribute
 * @param <Y> the type parameter for excel file attribute
 */
public interface ExcelColumnConverter<X,Y> {
    /**
     * Convert to entity attribute x.
     *
     * @param data the data
     * @return the converted value to be stored in the entity
     *             attribute
     */
    X convertToEntityAttribute(Y data);
}
