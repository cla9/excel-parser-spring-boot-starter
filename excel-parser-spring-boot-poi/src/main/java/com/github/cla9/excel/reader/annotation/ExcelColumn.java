package com.github.cla9.excel.reader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String headerName();
    int index() default UNDECIDED;
}
