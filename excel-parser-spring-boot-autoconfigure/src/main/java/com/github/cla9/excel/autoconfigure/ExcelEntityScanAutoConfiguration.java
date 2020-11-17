package com.github.cla9.excel.autoconfigure;

import com.github.cla9.excel.reader.config.ExcelEntityConfigurationExtension;
import com.github.cla9.excel.reader.config.ExcelTemplateConfigureRegistrar;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@ConditionalOnProperty(prefix = "excel.entity.validation", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(ExcelEntityConfigurationExtension.class)
@Import(ExcelTemplateConfigureRegistrar.class)
@AutoConfigureAfter(ExcelTemplateAutoConfiguration.class)
public class ExcelEntityScanAutoConfiguration {

}
