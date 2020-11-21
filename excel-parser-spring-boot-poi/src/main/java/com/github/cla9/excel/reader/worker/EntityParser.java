package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;

/**
 * The interface Entity parser.
 */
public interface EntityParser {
    /**
     * Parse.
     *
     * @param clazz the class type of entity
     */
    void parse(Class<?> clazz);

    /**
     * Gets entity metadata.
     *
     * @return the entity metadata
     */
    ExcelMetaModel getEntityMetadata();
}
