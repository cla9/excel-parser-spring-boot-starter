package com.github.cla9.excel.reader.config;

import java.util.stream.Stream;

public interface ExcelBodyConfigurationSource {
    Object getSource();
    Stream<String> getBasePackages();
}
