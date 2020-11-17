package com.github.cla9.excel.autoconfigure;

import com.github.cla9.excel.reader.config.ExcelReaderArgumentResolver;
import com.github.cla9.excel.reader.worker.ExcelTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AutoConfigureAfter(ExcelTemplateAutoConfiguration.class)
public class ExcelAppAutoConfiguration implements WebMvcConfigurer {
    private final ExcelTemplate excelTemplate;

    public ExcelAppAutoConfiguration(ExcelTemplate excelTemplate) {
        this.excelTemplate = excelTemplate;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ExcelReaderArgumentResolver(excelTemplate));
    }
}
