package com.github.cla9.excel.reader.annotation;

import com.github.cla9.excel.reader.config.ExcelEntityScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ExcelEntityScanRegistrar.class)
public @interface EnableExcelEntityScan {
    String[] value() default {};
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default{};
}
