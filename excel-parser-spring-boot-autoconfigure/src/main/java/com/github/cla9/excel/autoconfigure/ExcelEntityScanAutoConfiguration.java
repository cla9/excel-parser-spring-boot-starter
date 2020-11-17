package com.github.cla9.excel.autoconfigure;

import com.github.cla9.excel.reader.config.ExcelEntityConfigurationExtension;
import com.github.cla9.excel.reader.config.ExcelMetaModelMappingContext;
import com.github.cla9.excel.reader.config.ExcelTemplateConfigureRegistrar;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ConditionalOnMissingBean(ExcelEntityConfigurationExtension.class)
@Import(ExcelTemplateConfigureRegistrar.class)
public class ExcelEntityScanAutoConfiguration {

    @ConditionalOnMissingBean(ExcelMetaModelMappingContext.class)
    @Bean
    public ExcelMetaModelMappingContext getExcelMetaModelMappingContext(){
        return new ExcelMetaModelMappingContext();
    }
}
