package com.github.cla9.excel.reader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelRequestBody {
    String name() default "file";
    boolean mergeHeaderCheck() default false;
}
