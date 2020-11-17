package com.github.cla9.excel.reader.sheet;

import java.util.List;

public interface SheetHandler{
    List<String> getHeaderNames();
    int[] getOrder();
}
