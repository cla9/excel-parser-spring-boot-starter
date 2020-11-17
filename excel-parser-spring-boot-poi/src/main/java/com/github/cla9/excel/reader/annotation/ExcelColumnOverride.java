package com.github.cla9.excel.reader.annotation;

import static com.github.cla9.excel.reader.util.ExcelConstant.UNDECIDED;

public @interface ExcelColumnOverride {
    String headerName();
    int index() default UNDECIDED;
    ExcelColumn column();
}
