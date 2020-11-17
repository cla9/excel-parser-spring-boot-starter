package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.factory.ReaderFactory;
import org.springframework.context.annotation.Bean;

public class ExcelTemplateFactoryBean {
    private final ExcelMetaModelMappingContext metaModelMappingContext;

    public ExcelTemplateFactoryBean(ExcelMetaModelMappingContext metaModelMappingContext) {
        this.metaModelMappingContext = metaModelMappingContext;
    }

    @Bean
    public ReaderFactory getReaderFactory(){
        return new ReaderFactory(metaModelMappingContext);
    }
}
