package com.github.cla9.excel.reader.row;

import java.util.List;


public class SAXRowHandler implements RowHandler<List<String>> {
    private List<String> row;

    public String getValue(int i) {
        return (String)this.row.get(i);
    }
    public void setRow(final List<String> row) {
        this.row = row;
    }
}
