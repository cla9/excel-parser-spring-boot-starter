package com.github.cla9.excel.reader.annotation;

import com.github.cla9.excel.reader.factory.ReaderType;
import com.github.cla9.excel.reader.message.DefaultMessageRepository;
import com.github.cla9.excel.reader.message.ExcelMessageRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelBody {
    String sheetName();
    int dataRowPos() default UNDECIDED;
    int headerRowPos() default UNDECIDED;
    RowRange[] headerRowRange() default {};
    RowRange[] dataRowRange() default {};
    Class<? extends ExcelMessageRepository> messageSource() default DefaultMessageRepository.class;
    ReaderType type() default ReaderType.SAX;
}
