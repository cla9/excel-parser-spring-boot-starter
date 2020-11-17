package com.github.cla9.excel.autoconfigure;

import com.github.cla9.excel.reader.config.ExcelMetaModelMappingContext;
import com.github.cla9.excel.reader.factory.ReaderFactory;
import com.github.cla9.excel.reader.worker.ExcelTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


@Configuration
public class ExcelTemplateAutoConfiguration{

    @Bean
    @ConditionalOnMissingBean(ExcelMetaModelMappingContext.class)
    public static ExcelMetaModelMappingContext excelMetaModelMappingContext(){
        return new ExcelMetaModelMappingContext();
    }

    @Bean
    @DependsOn("excelMetaModelMappingContext")
    @ConditionalOnMissingBean(ReaderFactory.class)
    public static ReaderFactory readerFactory(ExcelMetaModelMappingContext excelMetaModelMappingContext){
        return new ReaderFactory(excelMetaModelMappingContext);
    }

    @ConditionalOnMissingBean(ExcelTemplate.class)
    @Bean
    @DependsOn("readerFactory")
    public static ExcelTemplate excelTemplate(ReaderFactory readerFactory){
        return new ExcelTemplate(readerFactory);
    }

}
