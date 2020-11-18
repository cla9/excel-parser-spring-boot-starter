package com.github.cla9.excel.reader.factory;

import com.github.cla9.excel.reader.annotation.ExcelBody;
import com.github.cla9.excel.reader.config.ExcelMetaModelMappingContext;
import com.github.cla9.excel.reader.worker.Reader;
import com.github.cla9.excel.reader.worker.SAXReader;
import com.github.cla9.excel.reader.worker.WorkBookReader;

/**
 * The type Reader factory.
 */
public class ReaderFactory {
    private final ExcelMetaModelMappingContext context;

    /**
     * Instantiates a new Reader factory.
     *
     * @param context the context
     */
    public ReaderFactory(ExcelMetaModelMappingContext context) {
        this.context = context;
    }

    /**
     * Create instance reader.
     *
     * @param <T>    the type parameter
     * @param type   the type
     * @param tClass the t class
     * @return the reader
     */
    public <T> Reader<T> createInstance(ReaderType type, Class<T> tClass)  {
        final boolean isCached = context.hasMetaModel(tClass);
        if (type == ReaderType.WORKBOOK) {
            return isCached ? new WorkBookReader<>(tClass, context.getMetaModel(tClass)) : new WorkBookReader<>(tClass);
        }
        return isCached ? new SAXReader<>(tClass, context.getMetaModel(tClass)) : new SAXReader<>(tClass);
    }

    /**
     * Create instance reader.
     *
     * @param <T>    the type parameter
     * @param tClass the t class
     * @return the reader
     */
    public  <T> Reader<T> createInstance(Class<T> tClass){
        final ExcelBody entity = tClass.getAnnotation(ExcelBody.class);
        return createInstance(entity.type(), tClass);
    }
}
