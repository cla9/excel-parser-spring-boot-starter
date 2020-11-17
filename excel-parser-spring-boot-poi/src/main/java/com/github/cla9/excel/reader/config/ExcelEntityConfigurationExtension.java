package com.github.cla9.excel.reader.config;

import com.github.cla9.excel.reader.annotation.ExcelBody;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

public class ExcelEntityConfigurationExtension implements ExcelEntityConfiguration {
    @Override
    public Collection<Class<? extends Annotation>> getIdentifyingAnnotations() {
        return List.of(ExcelBody.class);
    }
}
