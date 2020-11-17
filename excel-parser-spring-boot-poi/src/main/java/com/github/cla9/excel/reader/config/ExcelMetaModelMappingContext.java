package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.entity.ExcelMetaModel;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;


public class ExcelMetaModelMappingContext {
    private final Map<Class<?>, ExcelMetaModel> metadataCache;

    public ExcelMetaModelMappingContext() {
        metadataCache = new ConcurrentReferenceHashMap<>();
    }
    public boolean hasMetaModel(Class<?> clazz){
        return this.metadataCache.containsKey(clazz);
    }

    public  ExcelMetaModel getMetaModel(Class<?> clazz){
        return metadataCache.get(clazz);
    }
    void putMetaModel(Class<?> clazz, ExcelMetaModel metaModel){
        this.metadataCache.computeIfAbsent(clazz, a -> metaModel);
    }
}
