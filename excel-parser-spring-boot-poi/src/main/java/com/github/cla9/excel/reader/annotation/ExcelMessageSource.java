package com.github.cla9.excel.reader.annotation;

import com.github.cla9.excel.reader.message.ExcelMessageRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public  @interface ExcelMessageSource {
    Class<? extends ExcelMessageRepository> source();
}
