package com.github.cla9.excel.reader.worker;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;

public interface EntityParser {
    void parse(Class<?> clazz);
    ExcelMetaModel getEntityMetadata();
}
