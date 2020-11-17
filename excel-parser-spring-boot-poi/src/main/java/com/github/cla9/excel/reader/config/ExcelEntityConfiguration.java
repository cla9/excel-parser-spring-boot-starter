package com.github.cla9.excel.reader.config;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface ExcelEntityConfiguration {
    Collection<Class<? extends Annotation>> getIdentifyingAnnotations();
}
