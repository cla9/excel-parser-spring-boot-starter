package com.github.cla9.excel.reader.row;

public class WorkBookExcelColumnRowHandler extends WorkBookRowHandler{
    private final int[] order;

    public WorkBookExcelColumnRowHandler(final int[] order) {
        this.order = order;
    }

    @Override
    public String getValue(int i) {
        return super.getValue(order[i]);
    }
}
