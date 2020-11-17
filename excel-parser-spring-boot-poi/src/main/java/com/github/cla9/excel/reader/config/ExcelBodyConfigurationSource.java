package com.github.cla9.excel.reader.config;

import org.springframework.lang.Nullable;

import java.util.stream.Stream;

public interface ExcelBodyConfigurationSource {
    @Nullable
    Object getSource();
    Stream<String> getBasePackages();
    Class<?> getFactoryClass();
}
