package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.annotation.EnableExcelEntityScan;

import java.lang.annotation.Annotation;

public class ExcelTemplateConfigureRegistrar extends AbstractExcelConfigurationSourceSupport{
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableExcelEntityScan.class;
    }

    @Override
    protected Class<?> getConfiguration() { return EnableExcelEntityScanConfiguration.class; }

    @EnableExcelEntityScan
    private static class EnableExcelEntityScanConfiguration{}
}
